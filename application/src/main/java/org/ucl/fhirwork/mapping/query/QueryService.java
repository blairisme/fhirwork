/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query;

import org.ucl.fhirwork.configuration.*;

import javax.inject.Inject;
import java.util.Objects;

public class QueryService
{
    private ConfigurationService configurationService;

    @Inject
    public QueryService(ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }

    public String getQuery(String loinc, String ehrId)
    {
        MappingSpecification mappingSpecification = getMappingSpecification(loinc);
        return getQuery(mappingSpecification, ehrId);
    }

    private MappingSpecification getMappingSpecification(String loinc)
    {
        MappingConfiguration mappingConfiguration = configurationService.getConfiguration(Configuration.Mapping);
        for (MappingSpecification mappingSpecification: mappingConfiguration.getMappings()){
            if (Objects.equals(loinc, mappingSpecification.getLoinc())){
                return mappingSpecification;
            }
        }
        throw new UnsupportedOperationException();
    }

    private String getQuery(MappingSpecification mappingSpecification, String ehrId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getSelectStatement(mappingSpecification));
        stringBuilder.append(getFromStatement(ehrId));
        stringBuilder.append(getContainsStatement(mappingSpecification));
        return stringBuilder.toString();
    }

    private String getSelectStatement(MappingSpecification mappingSpecification)
    {
        String text = mappingSpecification.getText();
        MappingPath path = mappingSpecification.getPath();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append(getSelectStatement(text, path.getDate(), "date"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, path.getMagnitude(), "magnitude"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, path.getUnits(), "unit"));
        stringBuilder.append(" ");

        return stringBuilder.toString();
    }

    private String getSelectStatement(String text, String path, String label)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        stringBuilder.append("/");
        stringBuilder.append(path);
        stringBuilder.append(" as ");
        stringBuilder.append(label);
        return stringBuilder.toString();
    }

    private String getFromStatement(String ehrId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from EHR [ehr_id/value='");
        stringBuilder.append(ehrId);
        stringBuilder.append("'] ");
        return stringBuilder.toString();
    }

    private String getContainsStatement(MappingSpecification mappingSpecification)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contains COMPOSITION c ");
        stringBuilder.append("contains OBSERVATION ");
        stringBuilder.append(mappingSpecification.getText());
        stringBuilder.append("[");
        stringBuilder.append(mappingSpecification.getArchetype());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
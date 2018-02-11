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

import org.ucl.fhirwork.configuration.ConfigMissingException;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.MappingConfigData;

import javax.inject.Inject;

public class QueryService
{
    private ConfigService configuration;

    @Inject
    public QueryService(ConfigService configuration)
    {
        this.configuration = configuration;
    }

    public String getQuery(String loinc, String ehrId)
    {
        MappingConfigData mappingData = getMappingData(loinc);
        return getQuery(mappingData, ehrId);
    }

    private MappingConfigData getMappingData(String loinc)
    {
        try {
            return configuration.getMappingConfig(loinc);
        }
        catch (ConfigMissingException error) {
            throw new UnsupportedOperationException();
        }
    }

    private String getQuery(MappingConfigData mappingData, String ehrId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getSelectStatement(mappingData));
        stringBuilder.append(getFromStatement(ehrId));
        stringBuilder.append(getContainsStatement(mappingData));
        return stringBuilder.toString();
    }

    private String getSelectStatement(MappingConfigData mappingData)
    {
        String text = mappingData.getText();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append(getSelectStatement(text, mappingData.getDate(), "date"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, mappingData.getMagnitude(), "magnitude"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, mappingData.getUnit(), "unit"));
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

    private String getContainsStatement(MappingConfigData mappingData)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contains COMPOSITION c ");
        stringBuilder.append("contains OBSERVATION ");
        stringBuilder.append(mappingData.getText());
        stringBuilder.append("[");
        stringBuilder.append(mappingData.getArchetype());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
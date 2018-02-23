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
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.MappingConfig;
import org.ucl.fhirwork.configuration.data.MappingConfigData;
import org.ucl.fhirwork.configuration.exception.ConfigMissingException;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

public class QueryService
{
    private ConfigService configuration;

    @Inject
    public QueryService(ConfigService configuration)
    {
        this.configuration = configuration;
    }

    public boolean isSupported(String code)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        return mappingConfig.hasData(code);
    }

    public Collection<String> getSupported()
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        return mappingConfig.getCodes();
    }

    public String getQuery(String code, String ehrId)
    {
        try {
            MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
            MappingConfigData mappingData = mappingConfig.getData(code);
            return getQuery(mappingData, ehrId);
        }
        catch (ConfigMissingException error) {
            throw new UnsupportedOperationException(code);
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
/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.executor.observation;

import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.fhir.operations.observation.ReadObservationOperation;

import javax.inject.Inject;
import java.util.Collections;

public class ReadObservationExecutor implements Executor
{
    private EhrServer ehrServer;
    private ObservationFactory observationFactory;

    @Inject
    public ReadObservationExecutor(
            NetworkService networkService,
            ObservationFactory observationFactory)
    {
        this.ehrServer = networkService.getEhrServer();
        this.observationFactory = observationFactory;
    }

    @Override
    public void setOperation(Operation operation) {
        ReadObservationOperation readObservation = (ReadObservationOperation)operation;
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            String query = getAqlQuery();
            QueryBundle bundle = ehrServer.query(query);
            return observationFactory.fromQueryBundle("123", "3141-9", bundle);
        }
        catch (RestException error) {
            throw new ExecutionException(error);
        }
    }

    private String getAqlQuery()
    {
        return "select " +
                    "body_weight/data[at0002]/origin/value as date, " +
                    "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude as magnitude, " +
                    "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units as unit " +
                "from EHR [ehr_id/value='c831fe4d-0ce9-4a63-8bfa-2c51007f97e5'] " +
                "contains COMPOSITION c " +
                "contains OBSERVATION body_weight[openEHR-EHR-OBSERVATION.body_weight.v1]";
    }
}

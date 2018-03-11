/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.executor.patient;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.GeneralConfig;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.data.PersonUtils;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.patient.CreatePatientOperation;

import javax.inject.Inject;

/**
 * Instances of this class convert the create patient FHIR operation into the
 * appropriate EMPI service calls.
 *
 * @author Blair Butterworth
 */
public class CreatePatientExecutor implements Executor
{
    private Patient patient;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;
    private PersonFactory personFactory;
    private ConfigService configService;

    @Inject
    public CreatePatientExecutor(
        NetworkService networkService,
        PatientFactory patientFactory,
        PersonFactory personFactory,
        ConfigService configService)
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
        this.personFactory = personFactory;
        this.configService = configService;
    }

    @Override
    public void setOperation(Operation operation)
    {
        Validate.notNull(operation);
        CreatePatientOperation createPatient = (CreatePatientOperation)operation;
        patient = createPatient.getPatient();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try {
            Person personInput = personFactory.fromPatient(patient);
            //validatePerson(personInput);
            Person personOutput = empiServer.addPerson(personInput);
            return patientFactory.fromPerson(personOutput);
        }
        catch (Throwable cause){
            throw new ExecutionException(cause);
        }
    }

    private void validatePerson(Person person)
    {
        if (PersonUtils.hasIdentifier(person, getIllegalDomain())) {
            throw new IllegalStateException("Person contains illegal identifier: OpenEMPI");
        }
        if (! PersonUtils.hasIdentifier(person, getMandatoryDomain())) {
            throw new IllegalStateException("Person missing mandatory identifier: " + getMandatoryDomain());
        }
    }

    private String getIllegalDomain() {
        return "OpenEMPI";
    }

    private String getMandatoryDomain() {
        GeneralConfig generalConfig = configService.getConfig(ConfigType.General);
        return generalConfig.getEhrIdSystem();
    }
}

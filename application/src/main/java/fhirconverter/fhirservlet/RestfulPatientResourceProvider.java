package fhirconverter.fhirservlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.OperationOutcome;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PatchTypeEnum;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.MethodNotAllowedException;
import com.github.fge.jsonpatch.JsonPatch;
import fhirconverter.converter.PatientFHIR;
import fhirconverter.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class RestfulPatientResourceProvider implements IResourceProvider {
    private static Logger LOGGER = LogManager.getLogger(RestfulPatientResourceProvider.class);

    private static FHIRParser<Patient> parser = new FHIRParser<>(Patient.class);

    private PatientFHIR patientFHIR = new PatientFHIR();

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }


    @Create()
    public MethodOutcome createPatient(@ResourceParam Patient patient) {
        MethodOutcome retVal = new MethodOutcome();

        OperationOutcome outcome = new OperationOutcome();

        try {
            String jsonStringPatient = FhirContext.forDstu2().newJsonParser().encodeResourceToString(patient);
            LOGGER.info(jsonStringPatient);
            JSONObject resource = new JSONObject(jsonStringPatient);

            String reply = patientFHIR.create(resource);
            retVal.setId(new IdDt("Patient", reply, "1"));
            return retVal;
        } catch (DataFormatException | ClassCastException e) {
            LOGGER.info("Invalid Parameter Received", e);
            outcome.addIssue().setDiagnostics("Invalid Parameter Received");
            retVal.setOperationOutcome(outcome);
            return retVal;
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            outcome.addIssue().setDiagnostics(e.getMessage());
            retVal.setOperationOutcome(outcome);
            return retVal;
        }
    }

    ;

    @Search
    public List<Patient> getAllPatient() {


        try {
            List<Patient> reply = patientFHIR.search(new JSONObject());

            return reply;
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            throw new InternalErrorException(e.getMessage());
        }

    }

    ;

    @Search()
    public List<Patient> searchPatient(@OptionalParam(name = Patient.SP_FAMILY) StringDt familyName,
                                       @OptionalParam(name = Patient.SP_GIVEN) StringDt givenName,
                                       @OptionalParam(name = Patient.SP_GENDER) StringDt gender,
                                       @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate,
                                       @OptionalParam(name = Patient.SP_IDENTIFIER) TokenParam identifierToken) {

        try {
            JSONObject searchParams = new JSONObject();

            if (familyName != null)
                searchParams.put("family", familyName.getValue());
            if (givenName != null)
                searchParams.put("given", givenName.getValue());
            if (gender != null)
                searchParams.put("gender", gender.getValue());
            if (birthDate != null)
                searchParams.put("birthdate", birthDate.getValueAsString());
            if (identifierToken != null) {
                if(identifierToken.getSystem() == null)
                {
                    throw new DataFormatException("Incomplete identifier");
                }
                searchParams.put("identifier_domain", identifierToken.getSystem());
                searchParams.put("identifier_value", identifierToken.getValue());
            }

            LOGGER.info("FamilyName: " + familyName);
            LOGGER.info("GivenName: " + givenName);
            LOGGER.info("Gender: " + gender);
            LOGGER.info("BirthDate: " + birthDate);
            LOGGER.info("Identifiers: " + identifierToken);


            List<Patient> patients = patientFHIR.search(searchParams);
            return patients;
        } catch (DataFormatException e) {
            LOGGER.info("Invalid Parameter Received", e);
            throw new InvalidRequestException(e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            throw new InternalErrorException(e.getMessage());
        }
    }

    ;

    @Read
    public Patient getPatientById(@IdParam IdDt patientId) {
        try {

            patientId.getIdPartAsLong();
            Patient reply = patientFHIR.read(patientId.getIdPart());
//            Patient patient = (Patient) FhirContext.forDstu2().newJsonParser().parseResource(reply.toString());

            return reply;
        } catch (ResourceNotFoundException e) {
            LOGGER.info("Cannot find patient with id " + patientId);
            throw new ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException(patientId);
        } catch (NumberFormatException e) {
            LOGGER.info("Invalid id " + patientId.getIdPart());
            throw new ca.uhn.fhir.rest.server.exceptions.InvalidRequestException(patientId.getIdPart());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException(e.getMessage());

        }
    }

    ;

    @Update
    public MethodOutcome updatePatient(@IdParam IdDt patientId, @ResourceParam Patient patient) {
        MethodOutcome retVal = new MethodOutcome();

        OperationOutcome outcome = new OperationOutcome();


        try {
            patientId.getIdPartAsLong();
            String jsonStringPatient = FhirContext.forDstu2().newJsonParser().encodeResourceToString(patient);
            JSONObject resource = new JSONObject(jsonStringPatient);
            String reply = patientFHIR.update(patientId.getIdPart(), resource);

//            if(reply.equals("Created"))
//                response.status(201);
//            else
//                response.status(200);

            return retVal;
        } catch (DataFormatException | ClassCastException e) {
            LOGGER.info("Invalid Parameter Received", e);
            throw new ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException(patientId);
        } catch (ResourceNotFoundException e) {
            LOGGER.info("No Resource Found for id " + patientId.getIdPart(), e);
            throw new ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException(patientId);
        } catch (NumberFormatException e) {
            LOGGER.info("Unacceptable id " + patientId.getIdPart(), e);
            throw new ca.uhn.fhir.rest.server.exceptions.InvalidRequestException(patientId.getIdPart());
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            throw new ca.uhn.fhir.rest.server.exceptions.InternalErrorException(patientId.getIdPart());
        }
    }

    ;

    @Patch
    public OperationOutcome patchPatient(@IdParam IdDt patientId, PatchTypeEnum patchType,
                                         @ResourceParam String body) {

        OperationOutcome retVal = new OperationOutcome();


        try {
            patientId.getIdPartAsLong();

            JsonPatch patch = null;
            if (patchType == PatchTypeEnum.JSON_PATCH) {
                patch = parser.parseJSONPatch(body);
            }
            if (patchType == PatchTypeEnum.XML_PATCH) {
                throw new DataFormatException();
            }
            String reply = patientFHIR.patch(patientId.getIdPart(), patch);
//            retVal.getText().setDiv(reply);
            return retVal;
        } catch (DataFormatException e) {
            LOGGER.info("Invalid Parameter Received", e);
            throw new InvalidRequestException(e.getMessage());
        } catch (ResourceNotFoundException e) {

            LOGGER.info("No Resource Found for id " + patientId.getIdPart(), e);
            throw new ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException(patientId);
        } catch (NumberFormatException e) {

            LOGGER.info("Unacceptable id " + patientId.getIdPart(), e);
            throw new InvalidRequestException(e.getMessage());
        } catch (IOException e) {
            LOGGER.info("Invalid Patch Received", e);
            throw new InvalidRequestException(e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            throw new ca.uhn.fhir.rest.server.exceptions.InternalErrorException(patientId.getIdPart());
        }
    }

    ;

    @Delete
    public void deletePatient(@IdParam IdDt patientId) {

        try {
            patientId.getIdPartAsLong();
            String reply = patientFHIR.delete(patientId.getIdPart());
            return;
        } catch (NumberFormatException e) {
            LOGGER.info("Unacceptable id " + patientId.getIdPart(), e);
            throw new InvalidRequestException(e.getMessage());
        } catch (IOException e) {
            LOGGER.info("Not able to delete id " + patientId.getIdPart(), e);
            throw new MethodNotAllowedException(e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Exception Caught", e);
            throw new InternalErrorException(e.getMessage());
        }
    }

}

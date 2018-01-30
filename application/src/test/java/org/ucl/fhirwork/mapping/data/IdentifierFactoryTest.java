package org.ucl.fhirwork.mapping.data;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.parser.IParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IdentifierFactoryTest
{
    /*
    @Test
    public void fromIdTest() throws IOException
    {
        IdentifierFactory identifierFactory = new IdentifierFactory();
        Patient patient = readPatient("fhir/PatientExample.json");

        IdDt id = patient.getId();
        Identifier identifier = identifierFactory.fromId(id);

        Assert.assertEquals("1", identifier.getIdentifier());
        Assert.assertEquals("FHIR", identifier.getIdentifierDomain().getIdentifierDomainName());
    }
    */
    @Test
    public void fromIdentifierTest() throws IOException
    {
        IdentifierFactory identifierFactory = new IdentifierFactory();
        Patient patient = readPatient("fhir/PatientExample.json");

        IdentifierDt id = patient.getIdentifier().get(0);
        Identifier identifier = identifierFactory.fromId(id);

        Assert.assertEquals("568749875445698798988873", identifier.getIdentifier());
        Assert.assertEquals("OpenMRS", identifier.getIdentifierDomain().getIdentifierDomainName());
    }

    private Patient readPatient(String resource) throws IOException
    {
        IParser serializer = FhirContext.forDstu2().newJsonParser();
        String person = readResource(resource);
        return (Patient)serializer.parseResource(person);
    }

    private String readResource(String resource) throws IOException
    {
        URL templateUrl = Thread.currentThread().getContextClassLoader().getResource(resource);
        File templateFile = new File(templateUrl.getPath());
        return FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
    }
}

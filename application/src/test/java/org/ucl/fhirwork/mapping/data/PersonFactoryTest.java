/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.data;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.parser.IParser;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.network.empi.data.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PersonFactoryTest
{
    @Test
    public void fromPatientTest() throws IOException
    {
        IdentifierFactory identifierFactory = new IdentifierFactory();
        PersonFactory personFactory = new PersonFactory(identifierFactory);

        Patient patient = readPatient("fhir/PatientExample.json");
        Person person = personFactory.fromPerson(patient);

        Assert.assertEquals("Kathrin Mary", person.getGivenName());
        Assert.assertEquals("Williams", person.getFamilyName());
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

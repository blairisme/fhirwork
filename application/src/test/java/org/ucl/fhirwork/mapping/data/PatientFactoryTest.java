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

import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.Gender;
import org.ucl.fhirwork.network.empi.data.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PatientFactoryTest
{
    @Test
    public void fromPersonTest() throws IOException
    {
        GenderFactory genderFactory = new GenderFactory();
        IdentifierFactory identifierFactory = new IdentifierFactory();
        PatientFactory patientFactory = new PatientFactory(genderFactory, identifierFactory);

        Person person = readPerson("empi/PersonExample.xml");
        Patient patient = patientFactory.fromPerson(person);

        Assert.assertEquals("123", patient.getId().getIdPart());

        List<IdentifierDt> identifiers = patient.getIdentifier();
        Assert.assertEquals(2, identifiers.size());
        Assert.assertEquals("568749875445698798988873", identifiers.get(0).getValue());
        Assert.assertEquals("OpenMRS", identifiers.get(0).getSystem());
        Assert.assertEquals("2b869d20-6ccc-11e7-a2fc-0242ac120003", identifiers.get(1).getValue());
        Assert.assertEquals("OpenEMPI", identifiers.get(1).getSystem());

        List<HumanNameDt> names = patient.getName();
        Assert.assertEquals(1, names.size());

        HumanNameDt name = names.get(0);
        Assert.assertEquals("Kathrin", name.getGiven().get(0).toString());
        Assert.assertEquals("Williams", name.getFamily().get(0).toString());

        AdministrativeGenderEnum gender = patient.getGenderElement().getValueAsEnum();
        Assert.assertNotNull(gender);
        Assert.assertEquals(AdministrativeGenderEnum.FEMALE, gender);
    }

    private Person readPerson(String resource) throws IOException
    {
        Serializer serializer = new XmlSerializer();
        String person = readResource(resource);
        return serializer.deserialize(person, Person.class);
    }

    private String readResource(String resource) throws IOException
    {
        URL templateUrl = Thread.currentThread().getContextClassLoader().getResource(resource);
        File templateFile = new File(templateUrl.getPath());
        return FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
    }
}

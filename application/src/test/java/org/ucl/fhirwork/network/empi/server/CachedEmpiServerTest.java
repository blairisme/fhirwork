/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
import org.ucl.fhirwork.network.empi.data.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CachedEmpiServerTest
{
    private BasicEmpiServer basicServer;
    private CachedEmpiServer cachedServer;

    @Before
    public void setup() {
        basicServer = Mockito.mock(BasicEmpiServer.class);
        cachedServer = new CachedEmpiServer(basicServer);
    }

    @Test
    public void addTest() throws Exception {
        Person person = newPersonMock("1", "foo");
        InternalIdentifier identifier = person.getInternalIdentifier();

        Mockito.when(basicServer.addPerson(person)).thenReturn(person);
        Person expected = cachedServer.addPerson(person);

        Person actual = cachedServer.loadPerson(identifier);
        Assert.assertEquals(expected, actual);
        Mockito.verify(basicServer, Mockito.never()).loadPerson(identifier);
    }

    @Test
    public void loadTest() throws Exception {
        Person person = newPersonMock("1", "foo");
        InternalIdentifier identifier = person.getInternalIdentifier();

        Mockito.when(basicServer.loadPerson(identifier)).thenReturn(person);
        Person expected = cachedServer.loadPerson(identifier);
        Mockito.verify(basicServer, Mockito.times(1)).loadPerson(identifier);

        Person actual = cachedServer.loadPerson(identifier);
        Mockito.verify(basicServer, Mockito.times(1)).loadPerson(identifier);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateTest() throws Exception {
        Person person = newPersonMock("1", "foo");
        InternalIdentifier identifier = person.getInternalIdentifier();

        Mockito.when(basicServer.addPerson(person)).thenReturn(person);
        Mockito.when(basicServer.updatePerson(person)).thenReturn(person);

        cachedServer.addPerson(person);
        person.setGivenName("foo");
        Person expected = cachedServer.updatePerson(person);

        Person actual = cachedServer.loadPerson(identifier);
        Assert.assertEquals(expected, actual);
        Mockito.verify(basicServer, Mockito.never()).loadPerson(identifier);
    }

    @Test
    public void removeTest() throws Exception {
        Person person = newPersonMock("1", "foo");
        InternalIdentifier identifier = person.getInternalIdentifier();

        Mockito.when(basicServer.addPerson(person)).thenReturn(person);
        Mockito.when(basicServer.loadPerson(identifier)).thenReturn(person);

        cachedServer.addPerson(person);
        cachedServer.removePerson(identifier);

        Mockito.verify(basicServer, Mockito.times(0)).loadPerson(identifier);
        cachedServer.loadPerson(identifier);
        Mockito.verify(basicServer, Mockito.times(1)).loadPerson(identifier);
    }

    @Test
    public void loadAllTest() throws Exception {
        Mockito.when(basicServer.loadAllPersons(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());

        cachedServer.loadAllPersons(0, 100);
        Mockito.verify(basicServer, Mockito.times(1)).loadAllPersons(0, 100);

        cachedServer.loadAllPersons(0, 100);
        Mockito.verify(basicServer, Mockito.times(2)).loadAllPersons(0, 100);

        cachedServer.loadAllPersons(0, 100);
        Mockito.verify(basicServer, Mockito.times(3)).loadAllPersons(0, 100);
    }

    @Test
    public void findTest() throws Exception {
        Person person1 = newPersonMock("1", "foo");
        Person person2 = newPersonMock("2", "bar");
        Person person3 = newPersonMock("2", "bar");
        List<Person> people = Arrays.asList(person1, person2);

        Mockito.when(basicServer.findPersons(person1)).thenReturn(people);
        Mockito.when(basicServer.addPerson(person3)).thenReturn(person3);
        Mockito.when(basicServer.updatePerson(person3)).thenReturn(person3);

        Collection<Person> result1 = cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(1)).findPersons(person1); //1 times = empty cache
        Collection<Person> result2 = cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(1)).findPersons(person1); //1 times = cached result
        Assert.assertEquals(result1, result2);

        cachedServer.addPerson(person3);
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(2)).findPersons(person1); //2 times = invalidated cache
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(2)).findPersons(person1); //2 times = cached result

        cachedServer.updatePerson(person3);
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(3)).findPersons(person1); //3 times = invalidated cache
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(3)).findPersons(person1); //3 times = cached result

        cachedServer.removePerson(person3.getInternalIdentifier());
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(4)).findPersons(person1); //4 times = invalidated cache
        cachedServer.findPersons(person1);
        Mockito.verify(basicServer, Mockito.times(4)).findPersons(person1); //4 times = cached result
    }

    private Person newPersonMock(String id, String name) {
        Person result = new Person();
        result.setPersonId(id);
        result.setGivenName(name);
        return result;
    }
}

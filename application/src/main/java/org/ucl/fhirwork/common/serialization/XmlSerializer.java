/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.serialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlSerializer implements Serializer
{
    public <T> String serialize(T value, Class<T> type)
    {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(type);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(value, stringWriter);

            return stringWriter.toString();
        }
        catch (JAXBException e) {
            throw new SerializationException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(String value, Class<T> type)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader stringReader = new StringReader(value);
            return (T)unmarshaller.unmarshal(stringReader);
        }
        catch (JAXBException e) {
            throw new SerializationException(e);
        }
    }
}

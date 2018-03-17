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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Instances of this class serialize objects into their equivalent XML
 * representation. Methods are provided to convert Java objects into XML
 * and XML into Java objects.
 *
 * @author Blair Butterworth
 */
public class XmlSerializer implements Serializer
{
    @Override
    public <T> String serialize(T value, Class<T> type) throws SerializationException
    {
        StringWriter writer = new StringWriter();
        serialize(value, type, writer);
        return writer.toString();
    }

    @Override
    public <T> void serialize(T value, Class<T> type, Writer writer) throws SerializationException
    {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(value, writer);
        }
        catch (JAXBException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T deserialize(String value, Class<T> type) throws SerializationException
    {
        StringReader reader = new StringReader(value);
        return deserialize(reader, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Reader reader, Class<T> type) throws SerializationException
    {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T)unmarshaller.unmarshal(reader);
        }
        catch (JAXBException e) {
            throw new SerializationException(e);
        }
    }
}

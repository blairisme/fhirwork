package org.ucl.fhirwork.integration.serialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XmlSerializer
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
}

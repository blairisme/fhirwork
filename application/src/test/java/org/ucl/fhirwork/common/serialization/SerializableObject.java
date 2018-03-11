package org.ucl.fhirwork.common.serialization;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "serialized")
@SuppressWarnings("unused")
class SerializableObject {
    private String name;
    private String address;

    public SerializableObject() {
    }

    public SerializableObject(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

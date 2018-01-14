package fhirconverter.fhirservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Representation{
    XML,
    JSON,
    UNKNOWN;

    private static Logger LOGGER = LogManager.getLogger(Representation.class);

    private static final String XML_STRINGS_REGEX = "xml|text/xml|application/xml|application/fhir xml";
    private static final String JSON_STRINGS_REGEX = "json|text/json|application/json|application/fhir json";

    public static Representation fromString(String repr_string) {
        LOGGER.debug("Format Received: " + repr_string);
        if(repr_string == null)
            return UNKNOWN;

        Representation format = UNKNOWN;

        if (repr_string.matches(JSON_STRINGS_REGEX)) {
            format = Representation.JSON;
        }
        else if (repr_string.matches(XML_STRINGS_REGEX)){
            format = Representation.XML;
        }

        return format;
    }
}


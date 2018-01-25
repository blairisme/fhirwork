package org.ucl.fhirwork.integration.ehr.model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TemplateReference
{
    private String resource;

    public TemplateReference(String resource){
        this.resource = resource;
    }

    public String getContent() throws IOException {
        URL templateUrl = Thread.currentThread().getContextClassLoader().getResource(resource);
        File templateFile = new File(templateUrl.getPath());
        return FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
    }
}

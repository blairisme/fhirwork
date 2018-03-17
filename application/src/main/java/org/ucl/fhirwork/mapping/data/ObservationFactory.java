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

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import org.ucl.fhirwork.mapping.query.scripted.ScriptObservation;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Instances of this class create {@link Observation} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class ObservationFactory
{
    @Inject
    public ObservationFactory(){
    }

    public Observation from(String patientId, String code, QuantityDt quantity, DateTimeDt effective)
    {
        Observation observation = new Observation();
        observation.setId(newId());
        observation.setSubject(newSubject(patientId));
        observation.setValue(quantity);
        observation.setCode(newCode(code));
        observation.setEffective(effective);
        return observation;
    }

    public Observation from(ScriptObservation scriptObservation, String patient, String code)
     {
        Observation observation = new Observation();
        observation.setId(newId());
        observation.setSubject(newSubject(patient));
        observation.setValue(newQuantity(scriptObservation));
        observation.setCode(newCode(code));
        observation.setEffective(newEffective(scriptObservation));
        return observation;
    }

    private String newId()
    {
        return String.valueOf(ThreadLocalRandom.current().nextInt());
    }

    private CodeableConceptDt newCode(String loinc)
    {
        Map<String, String> codeMap = getCodeMap();

        CodeableConceptDt code = new CodeableConceptDt();
        code.addCoding(new CodingDt("http://loinc.org", loinc));
        code.setText(codeMap.get(loinc));
        return code;
    }

    //TODO: Remove
    private Map<String, String> getCodeMap()
    {
        Map<String, String> codeMap = new HashMap<>();
        codeMap.put("3141-9", "Weight");
        codeMap.put("8302-2", "Height");
        codeMap.put("39156-5", "BMI");
        codeMap.put("8287-5", "Head circumference");
        codeMap.put("37362-1", "XR Bone age");
        return codeMap;
    }

    private ResourceReferenceDt newSubject(String patientId)
    {
        ResourceReferenceDt subject = new ResourceReferenceDt();
        subject.setReference(patientId);
        return subject;
    }

    private QuantityDt newQuantity(ScriptObservation scriptObservation)
    {
        QuantityDt quantity = new QuantityDt();
        quantity.setValue(scriptObservation.getValue());
        quantity.setUnit(scriptObservation.getUnit());
        quantity.setCode(scriptObservation.getUnit());
        quantity.setSystem(scriptObservation.getUnitSystem());
        return quantity;
    }

    private DateTimeDt newEffective(ScriptObservation scriptObservation)
    {
        return new DateTimeDt(scriptObservation.getDate());
    }
}

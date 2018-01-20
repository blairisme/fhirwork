/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.utils;

import org.ucl.fhirwork.integration.fhir.model.FhirPatient;
import org.ucl.fhirwork.integration.fhir.model.Name;

import java.util.Objects;

public class NameUtils
{
    public static boolean hasGivenName(FhirPatient patient, String name)
    {
        for (Name patientName: patient.getName()) {
            for (String givenName: patientName.getGiven()){
                if (Objects.equals(givenName, name))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

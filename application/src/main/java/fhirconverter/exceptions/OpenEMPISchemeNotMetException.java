/**
 * 
 */
package fhirconverter.exceptions;

/**
 * @author Shruti Sinha
 *
 */
public class OpenEMPISchemeNotMetException extends Exception{

    private static final long serialVersionUID = 67879777;
    
    //private String message;
     
    public OpenEMPISchemeNotMetException(String message) {
	 super(message);
    }
}



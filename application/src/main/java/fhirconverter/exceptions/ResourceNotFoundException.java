package fhirconverter.exceptions;

/*
 * @author Shruti Sinha
 *
 */
public class ResourceNotFoundException extends ConversionException{

    private static final long serialVersionUID = 67879730;
    
    //private String message;
     
    public ResourceNotFoundException(String message) {
	 super(message);
    }
}





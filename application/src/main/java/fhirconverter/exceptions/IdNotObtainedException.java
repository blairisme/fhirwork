package fhirconverter.exceptions;

/*
 * @author Shruti Sinha
 *
 */
public class IdNotObtainedException extends Exception{

    private static final long serialVersionUID = 6787973;
    
    public IdNotObtainedException(String message) {
           super(message);
        }
    
}


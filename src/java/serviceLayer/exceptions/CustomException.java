package serviceLayer.exceptions;

//Class to handle exceptions our own way. 
public class CustomException extends Exception {

    /**
     * Empty constructor
     */
    public CustomException() {
    }
    
    /**
     * Constructs an instance of it self with the message from the parameter.
     * @param msg String containing the message.
     */
    public CustomException(String msg) {
        super(msg);
    }


    
}


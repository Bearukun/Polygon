package serviceLayer.exceptions;

public class PolygonException extends Exception{

     /**
     * Creates a new instance of <code>PolygonException</code> without detail
     * message.
     */
    public PolygonException() {
    }

    /**
     * Constructs an instance of <code>PolygonException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PolygonException(String msg) {
        super(msg);
    }

    
}

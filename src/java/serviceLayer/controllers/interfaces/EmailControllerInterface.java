
package serviceLayer.controllers.interfaces;

import serviceLayer.exceptions.PolygonException;

public interface EmailControllerInterface {
    
     public void send(String to, String title, String body) throws PolygonException;
     
}

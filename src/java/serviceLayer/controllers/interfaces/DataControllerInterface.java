package serviceLayer.controllers.interfaces;

import serviceLayer.entities.Image;
import serviceLayer.exceptions.CustomException;

public interface DataControllerInterface {

    Image getImage(int image_id) throws CustomException;
    
    
    
    
}

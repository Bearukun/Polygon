package dataAccessLayer.mappers.interfaces;

import serviceLayer.entities.Image;
import serviceLayer.exceptions.CustomException;

public interface DataMapperInterface {

    public Image getImage(int image_id) throws CustomException;
    

    
}

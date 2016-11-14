package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.entities.Image;
import serviceLayer.exceptions.CustomException;

public class DataController implements DataControllerInterface {
    
    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public Image getImage(int image_id) throws CustomException {

        return dbfacade.getImage(image_id);

    }


    
}

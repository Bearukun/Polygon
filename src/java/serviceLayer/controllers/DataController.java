package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.entities.Image;


public class DataController implements DataControllerInterface {
    
    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public Image getImage(int image_id) throws Exception {

        return dbfacade.getImage(image_id);

    }


    
}

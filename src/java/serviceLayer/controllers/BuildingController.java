package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.enties.Building;
import serviceLayer.exceptions.CustomException;

public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    @Override
    public void createBuilding(int postcode, int user_id, String address, String city) throws CustomException {
        dbfacade.createBuilding(postcode, user_id, address, city);
    }

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {
        return dbfacade.getBuildings(user_id);
    }



    
}

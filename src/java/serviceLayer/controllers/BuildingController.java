package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.enties.Building;
import serviceLayer.exceptions.CustomException;

public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException {
        dbfacade.createBuilding( name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {
        return dbfacade.getBuildings(user_id);
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws CustomException {
        return dbfacade.getAllBuildings();
    }

    @Override
    public void editBuilding(int selectedBuilding, String addres, int postcode, String cit) throws CustomException {
        dbfacade.editBuilding(selectedBuilding, addres, postcode, cit);
    }
}

package serviceLayer.controllers.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.enties.Building;
import serviceLayer.exceptions.CustomException;


public interface BuildingControllerInterface {

    
    
    void createBuilding( String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException;
    
    void editBuilding(int selectedBuilding, String addres, int postcode, String cit) throws CustomException;
    
    ArrayList<Building> getBuildings (int user_id)throws CustomException;
    
    ArrayList<Building> getAllBuildings ()throws CustomException;
    
}

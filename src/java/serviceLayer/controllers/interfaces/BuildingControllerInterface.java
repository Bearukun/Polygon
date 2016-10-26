package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.enties.Building;
import serviceLayer.exceptions.CustomException;


public interface BuildingControllerInterface {

    
    
    void createBuilding(int user_id, String address, int postcode, String city) throws CustomException;
    
    ArrayList<Building> getBuildings (int user_id)throws CustomException;
}

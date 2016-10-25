package serviceLayer.controllers.interfaces;

import serviceLayer.exceptions.CustomException;


public interface BuildingControllerInterface {

    
    
    void createBuilding(int postcode, int user_id, String address, String city) throws CustomException;
    
}

package dataAccessLayer.interfaces;

import java.util.ArrayList;
import serviceLayer.enties.Building;
import serviceLayer.enties.User;
import serviceLayer.enties.User.type;
import serviceLayer.exceptions.CustomException;


public interface DBFacadeInterface {
    
    User getUser(String email) throws CustomException;
    
    void createUser(String email, String password) throws CustomException;
    
    ArrayList<Building> getBuildings(int user_id) throws CustomException;
    
    void createBuilding(int user_id, String address, int postcode, String city) throws CustomException;
    
    
}

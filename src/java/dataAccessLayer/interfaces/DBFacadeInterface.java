package dataAccessLayer.interfaces;

import serviceLayer.enties.User;
import serviceLayer.enties.User.type;
import serviceLayer.exceptions.CustomException;


public interface DBFacadeInterface {
    
    User getUser(String email) throws CustomException;
    
    void createUser(String email, String password, type type) throws CustomException;
    
}

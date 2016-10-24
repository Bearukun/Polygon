package dataAccessLayer.interfaces;

import serviceLayer.enties.User;
import serviceLayer.exceptions.CustomException;


public interface DBFacadeInterface {
    
    User getUser(String email) throws CustomException;
    
}

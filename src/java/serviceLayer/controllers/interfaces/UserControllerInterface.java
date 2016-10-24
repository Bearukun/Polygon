package serviceLayer.controllers.interfaces;

import serviceLayer.enties.User;
import serviceLayer.exceptions.CustomException;

public interface UserControllerInterface {

    User login(String email, String password) throws CustomException;

    User getUser(String email) throws CustomException;

    void createUser (String email, String password) throws CustomException;
    
}

package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

public interface UserControllerInterface {

    User login(String email, String password) throws CustomException;

    User getUser(String email) throws CustomException;

    void createUser (String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException;
    
    ArrayList<User> getUsers() throws CustomException;
        
    void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException;
    
}

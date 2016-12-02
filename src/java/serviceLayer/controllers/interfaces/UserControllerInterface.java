package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;

public interface UserControllerInterface {

    User login(String email, String password) throws Exception;
    
    User getUser(int user_id) throws Exception;

    void createUser (String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception;
    
    ArrayList<User> getUsers() throws Exception;
        
    void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception;
    
    void deleteUser(int user_id) throws Exception;
}

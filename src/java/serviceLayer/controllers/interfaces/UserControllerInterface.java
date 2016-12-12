package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

public interface UserControllerInterface {

    User login(String email, String password) throws PolygonException;
    
    User getUser(int user_id) throws PolygonException;

    void createUser (String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws PolygonException;
    
    ArrayList<User> getUsers() throws PolygonException;
        
    void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws PolygonException;
    
    void deleteUser(int user_id) throws PolygonException;
    
}

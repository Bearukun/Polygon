package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

public class UserController implements UserControllerInterface {

    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public User login(String email, String password) throws PolygonException {
        
        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {

            //Get user by that email from database.
            User user = dbfacade.checkLogin(email, password);
            
            //Check if user is null. (If null, no such user) 
            if (user != null) {
                
                    return user;

            }

        }

        return null;

    }


    @Override
    public User getUser(int user_id) throws PolygonException {
        
        return dbfacade.getUser(user_id);
    
    }
    
    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws PolygonException {

        //If input fields aren't empty
        if (!email.isEmpty() && !password.isEmpty() && email != null && password != null) {

            //Then create user with 'email', 'password' and the other fields.
            dbfacade.createUser(email, password, name, phone, company, address, postcode, city, type);

        } else {

            //Input fields must be empty, throw error. 
            throw new PolygonException("Be sure to fill out both fields!");

        }

    }

    @Override
    public ArrayList<User> getUsers() throws PolygonException {
        
        return dbfacade.getUsers();
    
    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws PolygonException {
    
        dbfacade.editUser(selectedUser, email, password, name, phone, company, address, postcode, city);

    }

    @Override
    public void deleteUser(int user_id) throws PolygonException {
        
        dbfacade.deleteUser(user_id);
        
    }

}

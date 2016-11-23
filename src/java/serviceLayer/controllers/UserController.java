package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.User;

public class UserController implements UserControllerInterface {

    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public User login(String email, String password) throws Exception {
        
        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {

            //Get user by that email from database.
            User user = getUser(email);
        System.out.println(user.getPassword()+" . Check for æøå in database user import");
            //Check if user is null. (If null, no such user) 
            if (user != null) {

                //If password is correct
                if (user.getPassword().equals(password)) {

                    return user;
                    //If password is incorrect.     
                } else {

                    throw new Exception("Password or username incorrect..!");

                }

            }

        }

        return null;

    }

    @Override
    public User getUser(String email) throws Exception {
        return dbfacade.getUser(email);
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception {

        //If input fields aren't empty
        if (!email.isEmpty() && !password.isEmpty() && email != null && password != null) {

            //Then create user with 'email', 'password' and the other fields.
            dbfacade.createUser(email, password, name, phone, company, address, postcode, city, type);

        } else {

            //Input fields must be empty, throw error. 
            throw new Exception("Be sure to fill out both fields!");

        }

    }

    @Override
    public ArrayList<User> getUsers() throws Exception {
        return dbfacade.getUsers();
    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception {
        dbfacade.editUser(selectedUser, email, password, name, phone, company, address, postcode, city);

    }

}

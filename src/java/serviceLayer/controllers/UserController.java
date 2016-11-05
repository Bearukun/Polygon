package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

public class UserController implements UserControllerInterface {

    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public User login(String email, String password) throws CustomException {

        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {

            //Get user by that email from database.
            User user = getUser(email);

            //Check if user is null. (If null, no such user) 
            if (user != null) {

                //If password is correct
                if (user.getPassword().equals(password)) {

                    return user;
                    //If password is incorrect.     
                } else {

                    throw new CustomException("Password or username incorrect..!");

                }

            }

        }

        return null;

    }

    @Override
    public User getUser(String email) throws CustomException {
        return dbfacade.getUser(email);
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {

        System.out.println("UserController");
        //If input fields aren't empty
        if (!email.isEmpty() && !password.isEmpty() && email != null && password != null) {
            
            //Then create user with 'email', 'password' and the other fields.
            System.out.println("if down to dbfacade");
            dbfacade.createUser(email, password, name, phone, company, address, postcode, city);

        } else {

            
            //Input fields must be empty, trow error. 
            throw new CustomException("Be sure to fill out both fields!");

        }

    }

    @Override
    public ArrayList<User> getUsers() throws CustomException {
        return dbfacade.getUsers();
    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {
    dbfacade.editUser(selectedUser, email, password, name, phone, company, address, postcode, city);    
    
    }

}

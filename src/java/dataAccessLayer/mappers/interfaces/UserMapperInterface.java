package dataAccessLayer.mappers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

public interface UserMapperInterface {

    public User checkLogin(String email, String password) throws PolygonException;
    
    public User getUser(int user_id) throws PolygonException;

    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws PolygonException;

    public ArrayList<User> getUsers() throws PolygonException;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws PolygonException;

    public void deleteUser(int user_id) throws PolygonException;
    
}

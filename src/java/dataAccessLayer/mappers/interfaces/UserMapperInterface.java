package dataAccessLayer.mappers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

public interface UserMapperInterface {

    public User getUser(String email) throws CustomException;

    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws CustomException;

    public ArrayList<User> getUsers() throws CustomException;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException;

}

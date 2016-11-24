package dataAccessLayer.mappers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.User;

public interface UserMapperInterface {

    public User getUser(String email) throws Exception;
    
    public User getUser(int user_id) throws Exception;

    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception;

    public ArrayList<User> getUsers() throws Exception;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception;

}

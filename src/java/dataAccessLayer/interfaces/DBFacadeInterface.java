package dataAccessLayer.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;


public interface DBFacadeInterface {
    
    User getUser(String email) throws CustomException;
    
    void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException;
    
    ArrayList<Building> getBuildings(int user_id) throws CustomException;
    
    ArrayList<Building> getAllBuildings() throws CustomException;
    
    void createBuilding( String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException;
    
    ArrayList<User> getUsers() throws CustomException;

    public void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException;
    
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException;
    
    public ArrayList<Area> getAreas(int building_id) throws CustomException;
    
    public void deleteArea(int area_id) throws CustomException;
    
    public ArrayList<Room> getRooms(int building_id) throws CustomException;
    
    public void createArea(String name, String description, int sqm, int building_id) throws CustomException;
}

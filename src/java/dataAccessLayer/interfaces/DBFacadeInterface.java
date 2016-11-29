package dataAccessLayer.interfaces;

import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Image;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

public interface DBFacadeInterface {

    User getUser(String email) throws Exception;
    
    User getUser(int user_id) throws Exception;

    void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception;

    ArrayList<Building> getBuildings(int user_id) throws Exception;

    ArrayList<Building> getAllBuildings() throws Exception;

    void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception;

    public void deleteBuilding(int building_id) throws Exception;

    ArrayList<User> getUsers() throws Exception;

    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception;
    
    void deleteUser(int user_id) throws Exception;

    public ArrayList<Area> getAreas(int building_id) throws Exception;

    public void deleteArea(int area_id) throws Exception;

    public ArrayList<Room> getRooms(int building_id) throws Exception;

    public void createArea(String name, String description, int sqm, int building_id) throws Exception;

    public void createRoom(String name, String description, int sqm, int area_id) throws Exception;

    public void deleteRoom(int room_id) throws Exception;

    public void toggleHealthcheck(int building_id, int healthcheck_pending) throws Exception;

    public void assignHealthcheck(int buildingId, int technicianId) throws Exception;

    //Functions to the DataMapper
    public Image getImage(int image_id) throws Exception;
    
    public Image getBuildingImage(int building_id) throws Exception;
    
    public Image getIssueImage(int issue_id) throws Exception;
    
    public boolean hasImage(DataMapperInterface.ImageType imageType, int issue_id, int building_id) throws Exception;

    public void updateImage(DataMapperInterface.ImageType imageType, int issue_id, int building_id, String img_name, InputStream img_file) throws Exception;
    
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;
    
    public void uploadBuildingImage(int building_id, String img_name, InputStream img_file) throws Exception;

    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception;
    
}

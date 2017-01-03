package dataAccessLayer.interfaces;

import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Document;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Image;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;


public interface DBFacadeInterface {

    User checkLogin(String email, String password) throws Exception;
    
    User getUser(int user_id) throws Exception;

    void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception;

    ArrayList<Building> getBuildings(int user_id) throws Exception;
    
    public Building getBuilding(int buildingId) throws Exception; 

    ArrayList<Building> getAllBuildings() throws Exception;

    void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception;

    public void deleteBuilding(int buildingId) throws Exception;

    ArrayList<User> getUsers() throws Exception;

    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception;
    
    void deleteUser(int user_id) throws Exception;

    public ArrayList<Area> getAreas(int buildingId) throws Exception;

    public void deleteArea(int areaId) throws Exception;

    public ArrayList<Room> getRooms(int buildingId) throws Exception;

    public void createArea(String name, String description, int sqm, int buildingId) throws Exception;

    public void createRoom(String name, String description, int sqm, int areaId) throws Exception;

    public void deleteRoom(int roomId) throws Exception;

    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws Exception;

    public void assignHealthcheck(int buildingId, int technicianId) throws Exception;

    //Functions to the DataMapper
    public Image getImage(int image_id) throws Exception;
    
    public Image getBuildingImage(int buildingId) throws Exception;
    
    public Image getIssueImage(int issue_id) throws Exception;
    
    public boolean hasImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId) throws Exception;

    public void updateImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws Exception;
    
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;
    
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception;

    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception;
    
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws Exception;
    
    public void deleteIssue(int issueId) throws Exception;
    
    public ArrayList<Healthcheck> getAllHealthchecks() throws Exception;
    
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws Exception;
    
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws Exception;
    
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws Exception;
    
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws Exception;
    
    public void deleteMoistureMeasurement(int moistId) throws Exception;
    
    public ArrayList<DamageRepair> getAllDamageRepairs() throws Exception;
    
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws Exception;
    
    public void deleteDamageRepair(int roomId) throws Exception;
    
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws Exception;
    
    public ArrayList<Document> getDocuments(int buildingId) throws Exception;
    
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws Exception;
    
    public void deleteDocument(int documentId) throws Exception;
    
    public Document getDocument(int documentId) throws Exception;
    
}

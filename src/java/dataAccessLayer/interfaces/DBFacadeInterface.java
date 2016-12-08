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
import serviceLayer.exceptions.PolygonException;

public interface DBFacadeInterface {

    User checkLogin(String email, String password) throws PolygonException;
    
    User getUser(int user_id) throws PolygonException;

    void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws PolygonException;

    ArrayList<Building> getBuildings(int user_id) throws PolygonException;
    
    public Building getBuilding(int buildingId) throws PolygonException; 

    ArrayList<Building> getAllBuildings() throws PolygonException;

    void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws PolygonException;

    public void deleteBuilding(int buildingId) throws PolygonException;

    ArrayList<User> getUsers() throws PolygonException;

    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws PolygonException;

    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws PolygonException;
    
    void deleteUser(int user_id) throws PolygonException;

    public ArrayList<Area> getAreas(int buildingId) throws PolygonException;

    public void deleteArea(int areaId) throws PolygonException;

    public ArrayList<Room> getRooms(int buildingId) throws PolygonException;

    public void createArea(String name, String description, int sqm, int buildingId) throws PolygonException;

    public void createRoom(String name, String description, int sqm, int areaId) throws PolygonException;

    public void deleteRoom(int roomId) throws PolygonException;

    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws PolygonException;

    public void assignHealthcheck(int buildingId, int technicianId) throws PolygonException;

    //Functions to the DataMapper
    public Image getImage(int image_id) throws PolygonException;
    
    public Image getBuildingImage(int buildingId) throws PolygonException;
    
    public Image getIssueImage(int issue_id) throws PolygonException;
    
    public boolean hasImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId) throws PolygonException;

    public void updateImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws PolygonException;
    
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws PolygonException;
    
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws PolygonException;

    public void acceptHealthcheck(int buildingId, int technicianId) throws PolygonException;
    
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws PolygonException;
    
    public void deleteIssue(int issueId) throws PolygonException;
    
    public ArrayList<Healthcheck> getAllHealthchecks() throws PolygonException;
    
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws PolygonException;
    
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws PolygonException;
    
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws PolygonException;
    
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws PolygonException;
    
    public void deleteMoistureMeasurement(int moistId) throws PolygonException;
    
    public ArrayList<DamageRepair> getAllDamageRepairs() throws PolygonException;
    
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws PolygonException;
    
    public void deleteDamageRepair(int roomId) throws PolygonException;
    
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws PolygonException;
    
    public ArrayList<Document> getDocuments(int buildingId) throws PolygonException;
    
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws PolygonException;
    
    public void deleteDocument(int documentId) throws PolygonException;
    
    public Document getDocument(int documentId) throws PolygonException;
    
}

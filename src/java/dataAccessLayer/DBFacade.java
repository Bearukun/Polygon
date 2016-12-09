package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import dataAccessLayer.mappers.BuildingMapper;
import dataAccessLayer.mappers.DataMapper;
import dataAccessLayer.mappers.UserMapper;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import dataAccessLayer.mappers.interfaces.UserMapperInterface;
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

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {

    UserMapperInterface umi = new UserMapper();
    BuildingMapperInterface bmi = new BuildingMapper();
    DataMapperInterface dmi = new DataMapper();

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws PolygonException {

        return bmi.getBuildings(user_id);

    }

    @Override
    public Building getBuilding(int buildingId) throws PolygonException {

        return bmi.getBuilding(buildingId);

    }

    @Override
    public User checkLogin(String email, String password) throws PolygonException {

        return umi.checkLogin(email, password);

    }

    @Override
    public User getUser(int user_id) throws PolygonException {
        return umi.getUser(user_id);
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws PolygonException {

        umi.createUser(email, password, name, phone, company, address, postcode, city, type);

    }

    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws PolygonException {
        bmi.createBuilding(name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public void deleteBuilding(int buildingId) throws PolygonException {
        bmi.deleteBuilding(buildingId);
    }

    @Override
    public ArrayList<User> getUsers() throws PolygonException {

        return umi.getUsers();

    }

    @Override
    public ArrayList<Building> getAllBuildings() throws PolygonException {

        return bmi.getAllBuildings();

    }

    @Override
    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws PolygonException {

        bmi.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);

    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws PolygonException {

        umi.editUser(selectedUser, email, password, name, phone, company, address, postcode, city);

    }

    @Override
    public ArrayList<Area> getAreas(int buildingId) throws PolygonException {
        return bmi.getAreas(buildingId);
    }

    @Override
    public ArrayList<Room> getRooms(int buildingId) throws PolygonException {
        return bmi.getRooms(buildingId);
    }

    @Override
    public void createArea(String name, String description, int sqm, int buildingId) throws PolygonException {
        bmi.createArea(name, description, sqm, buildingId);
    }

    @Override
    public void deleteArea(int areaId) throws PolygonException {
        bmi.deleteArea(areaId);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int areaId) throws PolygonException {
        bmi.createRoom(name, description, sqm, areaId);
    }

    @Override
    public void deleteRoom(int roomId) throws PolygonException {
        bmi.deleteRoom(roomId);
    }

    @Override
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws PolygonException {
        bmi.toggleHealthcheck(buildingId, healthcheck_pending);
    }

    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws PolygonException {
        bmi.assignHealthcheck(buildingId, technicianId);
    }

    //Functions to the DataMapper
    @Override
    public Image getImage(int image_id) throws PolygonException {
        return dmi.getImage(image_id);
    }

    @Override
    public Image getBuildingImage(int buildingId) throws PolygonException {
        return dmi.getBuildingImage(buildingId);
    }

    @Override
    public Image getIssueImage(int issue_id) throws PolygonException {
        return dmi.getIssueImage(issue_id);
    }

    @Override
    public boolean hasImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId) throws PolygonException {
        return dmi.hasImage(imageType, issue_id, buildingId);
    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws PolygonException {
        dmi.uploadIssueImage(issue_id, img_name, img_file);
    }

    @Override
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws PolygonException {
        dmi.uploadBuildingImage(buildingId, img_name, img_file);
    }

    @Override
    public void updateImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws PolygonException {
        dmi.updateImage(imageType, issue_id, buildingId, img_name, img_file);
    }

    @Override
    public void deleteUser(int user_id) throws PolygonException {
        umi.deleteUser(user_id);
    }

    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws PolygonException {
        bmi.acceptHealthcheck(buildingId, technicianId);
    }

    @Override
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws PolygonException {
        return bmi.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);
    }

    @Override
    public void deleteIssue(int issueId) throws PolygonException {
        bmi.deleteIssue(issueId);
    }
    
    @Override
    public ArrayList<Healthcheck> getAllHealthchecks() throws PolygonException {
        return bmi.getAllHealthchecks();
    }
    
    @Override
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws PolygonException {
        return bmi.getBuildingHealthchecks(buildingId);
    }

    @Override
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws PolygonException {
        return bmi.getHealthcheckIssues(healthcheckId);
    }

    @Override
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws PolygonException {
        bmi.registerMoistureMeasurement(roomId, measurePoint, measureValue);
    }

    @Override
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws PolygonException {
        return bmi.getAllMoistureMeasurements();
    }

    @Override
    public void deleteMoistureMeasurement(int moistId) throws PolygonException {
        bmi.deleteMoistureMeasurement(moistId);
    }

    @Override
    public ArrayList<DamageRepair> getAllDamageRepairs() throws PolygonException {
        return bmi.getAllDamageRepairs();
    }

    @Override
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws PolygonException{
        bmi.registerDamageRepair(roomId, damageTime, damageLocation, damageDetails, workDone, type);
    }

    @Override
    public void deleteDamageRepair(int roomId) throws PolygonException {
        bmi.deleteDamageRepair(roomId);
    }

    @Override
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws PolygonException {
        bmi.completeHealthcheck(condition, buildingResponsible, healthcheckId, buildingId);
    }

    @Override
    public ArrayList<Document> getDocuments(int buildingId) throws PolygonException {
        return dmi.getDocuments(buildingId);
    }

    @Override
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws PolygonException {
        dmi.uploadDocument(buildingId, documentName, documentType, document_file);
    }

    @Override
    public void deleteDocument(int documentId) throws PolygonException {
        dmi.deleteDocument(documentId);
    }

    @Override
    public Document getDocument(int documentId) throws PolygonException {
        return dmi.getDocument(documentId);
    }
}

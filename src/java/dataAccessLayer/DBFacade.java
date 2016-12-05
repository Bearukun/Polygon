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
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Image;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {

    UserMapperInterface umi = new UserMapper();
    BuildingMapperInterface bmi = new BuildingMapper();
    DataMapperInterface dmi = new DataMapper();

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws Exception {

        return bmi.getBuildings(user_id);

    }

    @Override
    public User checkLogin(String email, String password) throws Exception {

        return umi.checkLogin(email, password);

    }

    @Override
    public User getUser(int user_id) throws Exception {
        return umi.getUser(user_id);
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws Exception {

        umi.createUser(email, password, name, phone, company, address, postcode, city, type);

    }

    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception {
        bmi.createBuilding(name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public void deleteBuilding(int buildingId) throws Exception {
        bmi.deleteBuilding(buildingId);
    }

    @Override
    public ArrayList<User> getUsers() throws Exception {

        return umi.getUsers();

    }

    @Override
    public ArrayList<Building> getAllBuildings() throws Exception {

        return bmi.getAllBuildings();

    }

    @Override
    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception {

        bmi.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);

    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws Exception {

        umi.editUser(selectedUser, email, password, name, phone, company, address, postcode, city);

    }

    @Override
    public ArrayList<Area> getAreas(int buildingId) throws Exception {
        return bmi.getAreas(buildingId);
    }

    @Override
    public ArrayList<Room> getRooms(int buildingId) throws Exception {
        return bmi.getRooms(buildingId);
    }

    @Override
    public void createArea(String name, String description, int sqm, int buildingId) throws Exception {
        bmi.createArea(name, description, sqm, buildingId);
    }

    @Override
    public void deleteArea(int areaId) throws Exception {
        bmi.deleteArea(areaId);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int areaId) throws Exception {
        bmi.createRoom(name, description, sqm, areaId);
    }

    @Override
    public void deleteRoom(int roomId) throws Exception {
        bmi.deleteRoom(roomId);
    }

    @Override
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws Exception {
        bmi.toggleHealthcheck(buildingId, healthcheck_pending);
    }

    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws Exception {
        bmi.assignHealthcheck(buildingId, technicianId);
    }

    //Functions to the DataMapper
    @Override
    public Image getImage(int image_id) throws Exception {
        return dmi.getImage(image_id);
    }

    @Override
    public Image getBuildingImage(int buildingId) throws Exception {
        return dmi.getBuildingImage(buildingId);
    }

    @Override
    public Image getIssueImage(int issue_id) throws Exception {
        return dmi.getIssueImage(issue_id);
    }

    @Override
    public boolean hasImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId) throws Exception {
        return dmi.hasImage(imageType, issue_id, buildingId);
    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception {
        dmi.uploadIssueImage(issue_id, img_name, img_file);
    }

    @Override
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception {
        dmi.uploadBuildingImage(buildingId, img_name, img_file);
    }

    @Override
    public void updateImage(DataMapperInterface.ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws Exception {
        dmi.updateImage(imageType, issue_id, buildingId, img_name, img_file);
    }

    @Override
    public void deleteUser(int user_id) throws Exception {
        umi.deleteUser(user_id);
    }

    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception {
        bmi.acceptHealthcheck(buildingId, technicianId);
    }

    @Override
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws Exception {
        return bmi.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);
    }

    @Override
    public void deleteIssue(int issueId) throws Exception {
        bmi.deleteIssue(issueId);
    }
    
    @Override
    public ArrayList<Healthcheck> getAllHealthchecks() throws Exception {
        return bmi.getAllHealthchecks();
    }
    
    @Override
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws Exception {
        return bmi.getBuildingHealthchecks(buildingId);
    }

    @Override
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws Exception {
        return bmi.getHealthcheckIssues(healthcheckId);
    }

    @Override
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws Exception {
        bmi.registerMoistureMeasurement(roomId, measurePoint, measureValue);
    }

    @Override
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws Exception {
        return bmi.getAllMoistureMeasurements();
    }

    @Override
    public void deleteMoistureMeasurement(int moistId) throws Exception {
        bmi.deleteMoistureMeasurement(moistId);
    }

    @Override
    public ArrayList<DamageRepair> getAllDamageRepairs() throws Exception {
        return bmi.getAllDamageRepairs();
    }

    @Override
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, DamageRepair.type type) throws Exception{
        bmi.registerDamageRepair(roomId, damageTime, damageLocation, damageDetails, workDone, type);
    }
}

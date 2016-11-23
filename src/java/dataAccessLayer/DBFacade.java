package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import dataAccessLayer.mappers.BuildingMapper;
import dataAccessLayer.mappers.DataMapper;
import dataAccessLayer.mappers.UserMapper;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import dataAccessLayer.mappers.interfaces.UserMapperInterface;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Image;
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
    public User getUser(String email) throws Exception {

        return umi.getUser(email);

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
    public void deleteBuilding(int building_id) throws Exception {
        bmi.deleteBuilding(building_id);
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
    public ArrayList<Area> getAreas(int building_id) throws Exception {
        return bmi.getAreas(building_id);
    }

    @Override
    public ArrayList<Room> getRooms(int building_id) throws Exception {
        return bmi.getRooms(building_id);
    }

    @Override
    public void createArea(String name, String description, int sqm, int building_id) throws Exception {
        bmi.createArea(name, description, sqm, building_id);
    }

    @Override
    public void deleteArea(int area_id) throws Exception {
        bmi.deleteArea(area_id);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int area_id) throws Exception {
        bmi.createRoom(name, description, sqm, area_id);
    }

    @Override
    public void deleteRoom(int room_id) throws Exception {
        bmi.deleteRoom(room_id);
    }

    @Override
    public void toggleHealthcheck(int building_id, int healthcheck_pending) throws Exception {
        bmi.toggleHealthcheck(building_id, healthcheck_pending);
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
    public Image getBuildingImage(int building_id) throws Exception {
        return dmi.getBuildingImage(building_id);
    }

    @Override
    public Image getIssueImage(int issue_id) throws Exception {
        return dmi.getIssueImage(issue_id);
    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception {
        dmi.uploadIssueImage(issue_id, img_name, img_file);
    }

    @Override
    public void uploadBuildingImage(int building_id, String img_name, InputStream img_file) throws Exception {
        dmi.uploadBuildingImage(building_id, img_name, img_file);
    }

}

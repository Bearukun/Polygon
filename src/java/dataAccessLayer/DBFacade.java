package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import dataAccessLayer.mappers.BuildingMapper;
import dataAccessLayer.mappers.DataMapper;
import dataAccessLayer.mappers.UserMapper;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import dataAccessLayer.mappers.interfaces.UserMapperInterface;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Image;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {
    
    UserMapperInterface umi = new UserMapper();
    BuildingMapperInterface bmi = new BuildingMapper();
    DataMapperInterface dmi = new DataMapper();

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {

        return bmi.getBuildings(user_id);
       
    }

    @Override
    public User getUser(String email) throws CustomException {

        return umi.getUser(email);
        
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {

       umi.createUser(email, password, name, phone, company, address, postcode, city);
        
    }

    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id ) throws CustomException {

       bmi.createBuilding(name, address, postcode, city, construction_year, purpose, sqm, 0);

    }

    @Override
    public ArrayList<User> getUsers() throws CustomException {

        return umi.getUsers();
        
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws CustomException {

        return bmi.getAllBuildings();

    }

    @Override
    public void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException {
        
       bmi.viewBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);
        
    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {
        
        umi.editUser(0, email, password, name, phone, company, address, postcode, city);
        
    }

    @Override
    public ArrayList<Area> getAreas(int building_id) throws CustomException {
        return bmi.getAreas(building_id);
    }

    @Override
    public ArrayList<Room> getRooms(int building_id) throws CustomException {
        return bmi.getRooms(building_id);
    }

    @Override
    public void createArea(String name, String description, int sqm, int building_id) throws CustomException {
        bmi.createArea(name, description, sqm, building_id);
    }

    @Override
    public void deleteArea(int area_id) throws CustomException {
        bmi.deleteArea(area_id);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int area_id) throws CustomException {
        bmi.createRoom(name, description, sqm, area_id);
    }

    @Override
    public void deleteRoom(int room_id) throws CustomException {
        bmi.deleteRoom(room_id);
    }

    @Override
    public Image getImage(int image_id) throws CustomException {

        return dmi.getImage(image_id);
        
    }
}

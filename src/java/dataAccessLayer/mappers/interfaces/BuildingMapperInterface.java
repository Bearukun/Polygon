package dataAccessLayer.mappers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.CustomException;

public interface BuildingMapperInterface {

    public ArrayList<Building> getBuildings(int user_id) throws CustomException;

    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException;

    public ArrayList<Building> getAllBuildings() throws CustomException;

    public void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException;

    public ArrayList<Area> getAreas(int building_id) throws CustomException;
    
    public ArrayList<Room> getRooms(int building_id) throws CustomException;
}

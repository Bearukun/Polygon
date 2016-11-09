package serviceLayer.controllers.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.CustomException;


public interface BuildingControllerInterface {

    
    
    void createBuilding( String name, String address, Integer postcode, String city,  Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException;
    
    void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException;
    
    ArrayList<Building> getBuildings (int user_id)throws CustomException;
    
    ArrayList<Building> getAllBuildings() throws CustomException;
    
    public ArrayList<Area> getAreas(int building_id) throws CustomException;
    
    public ArrayList<Room> getRooms(int building_id) throws CustomException;
}

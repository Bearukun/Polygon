package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;


public interface BuildingControllerInterface {
    
    void createBuilding( String name, String address, Integer postcode, String city,  Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception;
    
    public void deleteBuilding(int building_id) throws Exception;
    
    void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception;
    
    ArrayList<Building> getBuildings (int user_id)throws Exception;
    
    ArrayList<Building> getAllBuildings() throws Exception;
    
    public ArrayList<Area> getAreas(int building_id) throws Exception;
    
    public void deleteArea(int area_id) throws Exception;
    
    public ArrayList<Room> getRooms(int building_id) throws Exception;
    
    public void createArea(String name, String description, int sqm, int building_id) throws Exception;
    
    public void createRoom(String name, String description, int sqm, int area_id) throws Exception;
    
    public void deleteRoom(int room_id) throws Exception;
    
    public void toggleHealthcheck(int building_id, int healthcheck_pending) throws Exception;
    
    public void assignHealthcheck(int buildingId, int technicianId) throws Exception; 
}

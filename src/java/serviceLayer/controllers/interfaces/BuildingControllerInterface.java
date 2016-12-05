package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.Room;


public interface BuildingControllerInterface {
    
    void createBuilding( String name, String address, Integer postcode, String city,  Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception;
    
    public void deleteBuilding(int buildingId) throws Exception;
    
    void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception;
    
    ArrayList<Building> getBuildings (int user_id)throws Exception;
    
    ArrayList<Building> getAllBuildings() throws Exception;
    
    public ArrayList<Area> getAreas(int buildingId) throws Exception;
    
    public void deleteArea(int areaId) throws Exception;
    
    public ArrayList<Room> getRooms(int buildingId) throws Exception;
    
    public void createArea(String name, String description, int sqm, int buildingId) throws Exception;
    
    public void createRoom(String name, String description, int sqm, int areaId) throws Exception;
    
    public void deleteRoom(int roomId) throws Exception;
    
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws Exception;
    
    public void assignHealthcheck(int buildingId, int technicianId) throws Exception;
    
    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception;
    
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws Exception;
    
    public void deleteIssue(int issueId) throws Exception;
    
    public ArrayList<Healthcheck> getAllHealthchecks() throws Exception;
    
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws Exception;
    
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws Exception;
    
}

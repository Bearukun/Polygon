    package serviceLayer.controllers.interfaces;

import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.PolygonException;


public interface BuildingControllerInterface {
    
    void createBuilding( String name, String address, Integer postcode, String city,  Integer construction_year, String purpose, Integer sqm, int user_id) throws PolygonException;
    
    public void deleteBuilding(int buildingId) throws PolygonException;
    
    void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws PolygonException;
    
    ArrayList<Building> getBuildings (int user_id)throws PolygonException;
    
    public Building getBuilding(int buildingId) throws PolygonException; 
    
    ArrayList<Building> getAllBuildings() throws PolygonException;
    
    public ArrayList<Area> getAreas(int buildingId) throws PolygonException;
    
    public void deleteArea(int areaId) throws PolygonException;
    
    public ArrayList<Room> getRooms(int buildingId) throws PolygonException;
    
    public void createArea(String name, String description, int sqm, int buildingId) throws PolygonException;
    
    public void createRoom(String name, String description, int sqm, int areaId) throws PolygonException;
    
    public void deleteRoom(int roomId) throws PolygonException;
    
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws PolygonException;
    
    public void assignHealthcheck(int buildingId, int technicianId) throws PolygonException;
    
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

}

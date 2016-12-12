package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.PolygonException;

/**
 * BuildingController for the service layer.
 */
public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws PolygonException {
        
        dbfacade.createBuilding( name, address, postcode, city, construction_year, purpose, sqm, user_id);
        
    }

    @Override
    public void deleteBuilding(int buildingId) throws PolygonException {
        
        dbfacade.deleteBuilding(buildingId);
        
    }
    
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws PolygonException {
        
        return dbfacade.getBuildings(user_id);
        
    }
 
    @Override
    public Building getBuilding(int buildingId) throws PolygonException {
        
        return dbfacade.getBuilding(buildingId);
        
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws PolygonException {
        
        return dbfacade.getAllBuildings();
        
    }

    @Override
    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws PolygonException {
        
        dbfacade.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);
        
    }

    @Override
    public ArrayList<Area> getAreas(int buildingId) throws PolygonException {
        
        return dbfacade.getAreas(buildingId);
        
    }

    @Override
    public ArrayList<Room> getRooms(int buildingId) throws PolygonException {
        
        return dbfacade.getRooms(buildingId);
        
    }

    @Override
    public void createArea(String name, String description, int sqm, int buildingId) throws PolygonException {
        
        dbfacade.createArea(name, description, sqm, buildingId);
        
    }

    @Override
    public void deleteArea(int areaId) throws PolygonException {
        
        dbfacade.deleteArea(areaId);
        
    }

    @Override
    public void createRoom(String name, String description, int sqm, int areaId) throws PolygonException {
        
        dbfacade.createRoom(name, description, sqm, areaId);
        
    }

    @Override
    public void deleteRoom(int roomId) throws PolygonException {
        
        dbfacade.deleteRoom(roomId);
        
    }

    @Override
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws PolygonException {
        
        dbfacade.toggleHealthcheck(buildingId, healthcheck_pending);
        
    }

    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws PolygonException {
        
        dbfacade.assignHealthcheck(buildingId, technicianId);
        
    }

    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws PolygonException {
        
        dbfacade.acceptHealthcheck(buildingId, technicianId);
        
    }

    @Override
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws PolygonException {
        
        return dbfacade.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);
        
    }

    @Override
    public void deleteIssue(int issueId) throws PolygonException {
        
        dbfacade.deleteIssue(issueId);
        
    }
    
    @Override
    public ArrayList<Healthcheck> getAllHealthchecks() throws PolygonException {
        
        return dbfacade.getAllHealthchecks();
        
    }
    
    @Override
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws PolygonException {
        
        return dbfacade.getBuildingHealthchecks(buildingId);
        
    }

    @Override
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws PolygonException {
        
        return dbfacade.getHealthcheckIssues(healthcheckId);
        
    }

    @Override
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws PolygonException {
        
        dbfacade.registerMoistureMeasurement(roomId, measurePoint, measureValue);
        
    }

    @Override
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws PolygonException {
        
        return dbfacade.getAllMoistureMeasurements();
        
    }

    @Override
    public void deleteMoistureMeasurement(int moistId) throws PolygonException {
        
        dbfacade.deleteMoistureMeasurement(moistId);
        
    }

    @Override
    public ArrayList<DamageRepair> getAllDamageRepairs() throws PolygonException {
        
        return dbfacade.getAllDamageRepairs();
        
    }

    @Override
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws PolygonException {
        
        dbfacade.registerDamageRepair(roomId, damageTime, damageLocation, damageDetails, workDone, type);
        
    }

    @Override
    public void deleteDamageRepair(int roomId) throws PolygonException {
        
        dbfacade.deleteDamageRepair(roomId);
        
    }

    @Override
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws PolygonException {
        
        dbfacade.completeHealthcheck(condition, buildingResponsible, healthcheckId, buildingId);
        
    }

}

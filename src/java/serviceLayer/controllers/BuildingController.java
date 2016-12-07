package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;

public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception {
        dbfacade.createBuilding( name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public void deleteBuilding(int buildingId) throws Exception {
        dbfacade.deleteBuilding(buildingId);
    }
    
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws Exception {
        return dbfacade.getBuildings(user_id);
    }
 
    @Override
    public Building getBuilding(int buildingId) throws Exception {
        return dbfacade.getBuilding(buildingId);
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws Exception {
        return dbfacade.getAllBuildings();
    }

    @Override
    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws Exception {
        dbfacade.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);
    }

    @Override
    public ArrayList<Area> getAreas(int buildingId) throws Exception {
        return dbfacade.getAreas(buildingId);
    }

    @Override
    public ArrayList<Room> getRooms(int buildingId) throws Exception {
        return dbfacade.getRooms(buildingId);
    }

    @Override
    public void createArea(String name, String description, int sqm, int buildingId) throws Exception {
        dbfacade.createArea(name, description, sqm, buildingId);
    }

    @Override
    public void deleteArea(int areaId) throws Exception {
        dbfacade.deleteArea(areaId);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int areaId) throws Exception {
        dbfacade.createRoom(name, description, sqm, areaId);
    }

    @Override
    public void deleteRoom(int roomId) throws Exception {
        dbfacade.deleteRoom(roomId);    
    }

    @Override
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws Exception {
        dbfacade.toggleHealthcheck(buildingId, healthcheck_pending);
    }

    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws Exception {
        dbfacade.assignHealthcheck(buildingId, technicianId);
    }

    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception {
        dbfacade.acceptHealthcheck(buildingId, technicianId);
    }

    @Override
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws Exception {
        return dbfacade.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);
    }

    @Override
    public void deleteIssue(int issueId) throws Exception {
        dbfacade.deleteIssue(issueId);
    }
    
    @Override
    public ArrayList<Healthcheck> getAllHealthchecks() throws Exception {
        return dbfacade.getAllHealthchecks();
    }
    
    @Override
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws Exception {
        return dbfacade.getBuildingHealthchecks(buildingId);
    }

    @Override
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws Exception {
        return dbfacade.getHealthcheckIssues(healthcheckId);
    }

    @Override
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws Exception {
        dbfacade.registerMoistureMeasurement(roomId, measurePoint, measureValue);
    }

    @Override
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws Exception {
        return dbfacade.getAllMoistureMeasurements();
    }

    @Override
    public void deleteMoistureMeasurement(int moistId) throws Exception {
        dbfacade.deleteMoistureMeasurement(moistId);
    }

    @Override
    public ArrayList<DamageRepair> getAllDamageRepairs() throws Exception {
        return dbfacade.getAllDamageRepairs();
    }

    @Override
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws Exception {
        dbfacade.registerDamageRepair(roomId, damageTime, damageLocation, damageDetails, workDone, type);
    }

    @Override
    public void deleteDamageRepair(int roomId) throws Exception {
        dbfacade.deleteDamageRepair(roomId);
    }

    @Override
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws Exception {
        dbfacade.completeHealthcheck(condition, buildingResponsible, healthcheckId, buildingId);
    }

}

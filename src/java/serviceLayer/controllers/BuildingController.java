package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;

public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws Exception {
        dbfacade.createBuilding( name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public void deleteBuilding(int building_id) throws Exception {
        dbfacade.deleteBuilding(building_id);
    }
    
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws Exception {
        return dbfacade.getBuildings(user_id);
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
    public ArrayList<Area> getAreas(int building_id) throws Exception {
        return dbfacade.getAreas(building_id);
    }

    @Override
    public ArrayList<Room> getRooms(int building_id) throws Exception {
        return dbfacade.getRooms(building_id);
    }

    @Override
    public void createArea(String name, String description, int sqm, int building_id) throws Exception {
        dbfacade.createArea(name, description, sqm, building_id);
    }

    @Override
    public void deleteArea(int area_id) throws Exception {
        dbfacade.deleteArea(area_id);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int area_id) throws Exception {
        dbfacade.createRoom(name, description, sqm, area_id);
    }

    @Override
    public void deleteRoom(int room_id) throws Exception {
        dbfacade.deleteRoom(room_id);    
    }

    @Override
    public void toggleHealthcheck(int building_id, int healthcheck_pending) throws Exception {
        dbfacade.toggleHealthcheck(building_id, healthcheck_pending);
    }

    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws Exception {
        dbfacade.assignHealthcheck(buildingId, technicianId);
    }

    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws Exception {
        dbfacade.acceptHealthcheck(buildingId, technicianId);
    }

    public void createIssue(int buildingId, int areaId, int roomId, String sueDescription, String sueTreatment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

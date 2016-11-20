package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.CustomException;

public class BuildingController implements BuildingControllerInterface{

    private final DBFacadeInterface dbfacade = new DBFacade();
    
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException {
        dbfacade.createBuilding( name, address, postcode, city, construction_year, purpose, sqm, user_id);
    }

    @Override
    public void deleteBuilding(int building_id) throws CustomException {
        dbfacade.deleteBuilding(building_id);
    }
    
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {
        return dbfacade.getBuildings(user_id);
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws CustomException {
        return dbfacade.getAllBuildings();
    }

    @Override
    public void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException {
        dbfacade.viewBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpose, sqm);
    }

    @Override
    public ArrayList<Area> getAreas(int building_id) throws CustomException {
        return dbfacade.getAreas(building_id);
    }

    @Override
    public ArrayList<Room> getRooms(int building_id) throws CustomException {
        return dbfacade.getRooms(building_id);
    }

    @Override
    public void createArea(String name, String description, int sqm, int building_id) throws CustomException {
        dbfacade.createArea(name, description, sqm, building_id);
    }

    @Override
    public void deleteArea(int area_id) throws CustomException {
        dbfacade.deleteArea(area_id);
    }

    @Override
    public void createRoom(String name, String description, int sqm, int area_id) throws CustomException {
        dbfacade.createRoom(name, description, sqm, area_id);
    }

    @Override
    public void deleteRoom(int room_id) throws CustomException {
        dbfacade.deleteRoom(room_id);    
    }

    @Override
    public void toggleHealthcheck(int building_id, int healthcheck_pending) throws CustomException {
        dbfacade.toggleHealthcheck(building_id, healthcheck_pending);
    }
}

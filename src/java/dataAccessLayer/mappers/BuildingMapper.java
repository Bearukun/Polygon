package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.CustomException;

public class BuildingMapper implements BuildingMapperInterface {

    //Declare and instantiate ArrayLists.
    private ArrayList<Building> userBuilding = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();

    
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {
        
        //Declare new Building.condation object, with the name condition.
        Building.condition condition;
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM building WHERE user_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, user_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                //If-condition, checking the condition of the building.
                //The local varible gets assigned the ENUM from the rs.
                if (rs.getString(7).equals(Building.condition.GOOD.toString())) {

                    condition = Building.condition.GOOD;

                } else if (rs.getString(7).equals(Building.condition.MEDIUM.toString())) {
                    
                    condition = Building.condition.MEDIUM;

                } else if (rs.getString(7).equals(Building.condition.POOR.toString())) {
                    
                    condition = Building.condition.POOR;
                
                } else {
                    
                    condition = Building.condition.NONE;
                
                }
          
                //int building_id, String name, String date_created, String address, int postcode, String city, condition condition, int construction_year, String purpose, int sqm) {
                userBuilding.add(new Building(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5), rs.getString(6), condition, rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getBoolean(12), rs.getInt(13), rs.getInt(14)));
            }
            
        } catch (Exception e) {
            
            throw new CustomException("SQL Error:@DBFacade.getBuildings."+e.getMessage());
        
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        //Return ArrayList of Building(s).
        return userBuilding;
        
    }

    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws CustomException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
                
        try {
         
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "INSERT INTO `polygon`.`building` (`name`, `address`, `postcode`, `city`, `construction_year`, `purpose`, `sqm`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert variables if into prepareStatement.
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, postcode);
            stmt.setString(4, city);
            stmt.setInt(5, construction_year);
            stmt.setString(6, purpose);
            stmt.setInt(7, sqm);
            stmt.setInt(8, user_id);
            
            //Currently disabled due to Customexception being thrown, even when the SQL statment has been adjusted to:
            //"INSERT INTO `polygon`.`building` (`address`, `postcode`, `city`, `user_id`, 'floor', 'description') VALUES (?, ?, ?, ?, ?, ?);";
            //stmt.setInt(5, floor);
            //stmt.setString(6, description);
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new CustomException("SQL Error: Connection problem.");

        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException ex) {
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws CustomException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.building;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();
            
            //Declare and instantiate condition.
            Building.condition condition;
            
            //Loop through the resultSet.
            while (rs.next()) {
                
                //Series of if-statements reading the condition and setting 
                //the local variable. 
                if (rs.getString(7).equals(Building.condition.GOOD.toString())) {

                    condition = Building.condition.GOOD;

                } else if (rs.getString(7).equals(Building.condition.MEDIUM.toString())) {
                    
                    condition = Building.condition.MEDIUM;

                } else if (rs.getString(7).equals(Building.condition.POOR.toString())) {
                    
                    condition = Building.condition.POOR;
                    
                } else {
                    
                    condition = Building.condition.NONE;
                    
                }
                
                //Add current building from RS into the allBuilding-ArrayList.
                allBuildings.add(new Building(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5), rs.getString(6), condition, rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getBoolean(12), rs.getInt(13), rs.getInt(14)));
                
            }
            
        } catch (Exception e) {
            
            throw new CustomException("SQL Error: getAllBuildingsFailed at DBFacade.");
            
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        return allBuildings;
        
    }

    
    @Override
    public ArrayList<Area> getAreas(int building_id) throws CustomException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM area WHERE building_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, building_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();
            
            //Loop through the resultSet.
            while (rs.next()) {
                buildingAreas.add(new Area(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error:@DBFacade.getAreas."+e.getMessage());
        }finally{
            //Try releasing objects. 
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                //Throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getAreas."+ex.getMessage());
            }
        }
        //Return ArrayList of Area(s).
        return buildingAreas;
    }
    
    @Override
    public void viewBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws CustomException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
             
        try {
           
            //Get connection object.
            con = DBConnection.getConnection();
            //String sql = "UPDATE polygon.building SET postcode=? WHERE building_id=?";
            String sql = "UPDATE polygon.building SET name=?, address=?, postcode=?, city=?, construction_year=?, purpose=?, sqm=? WHERE building_id=?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, buildingName);
            stmt.setString(2, addres);
            stmt.setInt(3, postcod);
            stmt.setString(4, cit);
            stmt.setInt(5, constructionYear);
            stmt.setString(6, purpose);
            stmt.setInt(7, sqm);
            stmt.setInt(8, selectedBuilding);
            //Execute update.
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new CustomException("SQL Error: Connection problem.");
            
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException ex) {
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

    @Override
    public ArrayList<Room> getRooms(int building_id) throws CustomException {
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            //String sql = "SELECT * FROM room WHERE building_id = ?";
            String sql = "SELECT room.room_id, room.name, room.description, room.sqm, room.moisture_scan, room.area_id FROM polygon.room join polygon.area on room.area_id = area.area_id and building_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, building_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();
            
            //Loop through the resultSet.
            while (rs.next()) {
                buildingRooms.add(new Room(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error:@DBFacade.getRooms."+e.getMessage());
        }finally{
            //Try releasing objects. 
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                //Throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getRooms."+ex.getMessage());
            }
        }
        //Return ArrayList of Room(s).
        return buildingRooms;
    }

    @Override
    public void createArea(String name, String description, int sqm, int building_id) throws CustomException {
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
             
        try {
            //Get connection object.
            con = DBConnection.getConnection();
            //String sql = "UPDATE polygon.building SET postcode=? WHERE building_id=?";
            String sql = "INSERT INTO polygon.area (name, description, sqm, building_id) VALUES (?, ?, ?, ?)";
            //"INSERT INTO `polygon`.`building` (`name`, `address`, `postcode`, `city`, `construction_year`, `purpose`, `sqm`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, sqm);
            stmt.setInt(4, building_id);
            //Execute update.
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new CustomException("SQL Error: Connection problem.");
        }finally{
            //Try releasing objects. 
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.createArea."+ex.getMessage());
            }
        }
    }
}

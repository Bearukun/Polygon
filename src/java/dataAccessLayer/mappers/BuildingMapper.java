package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.exceptions.PolygonException;

/**
 * Class dealing with building data
 */
public class BuildingMapper implements BuildingMapperInterface {

    //Declare and instantiate ArrayLists.
    private ArrayList<Building> userBuilding = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Healthcheck> buildingHealthchecks = new ArrayList();
    private ArrayList<MoistureInfo> allMoistureMeasurements = new ArrayList();
    private ArrayList<DamageRepair> allDamageRepairs = new ArrayList();
    private ArrayList<Healthcheck> allHealthchecks = new ArrayList();
    private ArrayList<Issue> healthcheckIssues = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();

    //FIX THIS ASAP
    java.util.Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());

    /**
     * Method to retrieve all buildings pertaining a specific user
     *
     * @param user_id int identifying the user whose buildings are to be
     * retrieved
     * @return An ArrayList of type Building
     * @throws PolygonException
     */
    @Override
    public ArrayList<Building> getBuildings(int user_id) throws PolygonException {

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

                userBuilding.add(new Building(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5), rs.getString(6), condition, rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13)));

            }

        } catch (Exception e) {

            throw new PolygonException("Exception:@BuildingMapper.getBuildings." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQL Exception:@Exception:@BuildingMapper.getBuildings." + e.getMessage());

            }

        }

        //Return ArrayList of Building(s).
        return userBuilding;

    }

    /**
     * Method to create a new building
     *
     * @param name String detailing the building's name
     * @param address String detailing the building's address
     * @param postcode int detailing the building's postcode
     * @param city String detailing the building's city
     * @param construction_year int detailing the building's year of
     * construction
     * @param purpose String detailing the building's designated purpose
     * @param sqm int detailing the building's size in square meters
     * @param user_id int identifying the user who needs to be linked to the new
     * building
     * @throws PolygonException
     */
    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id) throws PolygonException {

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

            //Execute update.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.createBuilding:" + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //Throw error if there's an SQLException. 
                throw new PolygonException("SQL Error:@DBFacade.getBuildings." + ex.getMessage());

            }

        }

    }

    /**
     * Method to delete a specific building
     *
     * @param buildingId int containing the id of which building is to be
     * deleted
     * @throws PolygonException
     */
    @Override
    public void deleteBuilding(int buildingId) throws PolygonException {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "DELETE FROM building WHERE building_id = ?;ALTER TABLE building AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, buildingId);
            //Execute update
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.deleteBuilding:" + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.deleteBuilding:" + e.getMessage());

            }

        }

    }

    /**
     * Method to retrieve all buildings
     *
     * @return An ArrayList of type Building
     * @throws PolygonException
     */
    @Override
    public ArrayList<Building> getAllBuildings() throws PolygonException {

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
                allBuildings.add(new Building(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5), rs.getString(6), condition, rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13)));

            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getAllBuildings:" + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException:@DBFacade.getBuildings." + e.getMessage());

            }

        }

        return allBuildings;

    }

    /**
     * Method to retrieve all areas pertaining a specific building
     *
     * @param buildingId int specifying which building's areas are to be
     * retrieved
     * @return An ArrayList of type Area
     * @throws PolygonException
     */
    @Override
    public ArrayList<Area> getAreas(int buildingId) throws PolygonException {

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
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {

                buildingAreas.add(new Area(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));

            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getAreas." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //Throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getAreas." + e.getMessage());

            }

        }

        //Return ArrayList of Area(s).
        return buildingAreas;

    }

    /**
     * Method to edit a building's details
     *
     * @param selectedBuilding int specifying which building's details are to be
     * edited
     * @param buildingName String detailing the building's name
     * @param addres String detailing the building's address
     * @param postcod int detailing the building's postcode
     * @param cit String detailing the building's city
     * @param constructionYear int detailing the building's year of construction
     * @param purpose String detailing the building's designated purpose
     * @param sqm int detailing the building's size in square metres
     * @throws PolygonException
     */
    @Override
    public void editBuilding(int selectedBuilding, String buildingName, String addres, int postcod, String cit, int constructionYear, String purpose, int sqm) throws PolygonException {

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

            throw new PolygonException("Exception@BuildingMapper.editBuilding:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper..editBuildings." + e.getMessage());

            }

        }

    }

    /**
     * Method to retrieve all rooms pertaining a specific building
     *
     * @param buildingId int specifying which building's rooms are to be
     * retrieved
     * @return An ArrayList of type Room
     * @throws PolygonException
     */
    @Override
    public ArrayList<Room> getRooms(int buildingId) throws PolygonException {
        
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
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                buildingRooms.add(new Room(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            
            }
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.getRooms:" + e.getMessage());
        
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException e) {
                
                //Throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getRooms." + e.getMessage());
            
            }
            
        }
        
        //Return ArrayList of Room(s).
        return buildingRooms;
        
    }

    /**
     * Method to create a new area
     *
     * @param name String detailing the name for the new area
     * @param description String detailing the description for the new area
     * @param sqm int detailing the number of square metres for the new area
     * @param buildingId String specifying the building for which the new area
     * needs creating
     * @throws PolygonException
     */
    @Override
    public void createArea(String name, String description, int sqm, int buildingId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "INSERT INTO polygon.area (name, description, sqm, building_id) VALUES (?, ?, ?, ?)";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, sqm);
            stmt.setInt(4, buildingId);
            //Execute update.
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.createArea:"+e.getMessage());
        
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper..createArea:" + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to delete an area
     *
     * @param areaId int specifying which area is to be deleted
     * @throws PolygonException
     */
    @Override
    public void deleteArea(int areaId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //String sql = "UPDATE polygon.building SET postcode=? WHERE building_id=?";
            String sql = "DELETE FROM area WHERE area_id = ?;ALTER TABLE area AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, areaId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.deleteArea:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.deleteArea." + e.getMessage());
            
            }
        
        }
    
    }

    /**
     * Method to create a new room
     *
     * @param name String detailing the name for the new room
     * @param description String detailing the description for the new room
     * @param sqm int detailing the number of square metres for the new room
     * @param areaId String specifying the area for which a new room needs
     * creating
     * @throws PolygonException
     */
    @Override
    public void createRoom(String name, String description, int sqm, int areaId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "INSERT INTO polygon.room (name, description, sqm, area_id) VALUES (?, ?, ?, ?)";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, sqm);
            stmt.setInt(4, areaId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.createRoom:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.createRoom:" + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to delete a room
     *
     * @param roomId int specifying which room is to be deleted
     * @throws PolygonException
     */
    @Override
    public void deleteRoom(int roomId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "DELETE FROM room WHERE room_id = ?;ALTER TABLE room AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.deleteRoom:" + e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("Exception@BuildingMapper.deleteRoom." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to change status for a healthcheck, either 'order' or 'cancel'
     *
     * @param buildingId int specifying which building for which the healthcheck
     * option needs toggling
     * @param healthcheck_pending int detailing the new status for the
     * healthcheck
     * @throws PolygonException
     */
    @Override
    public void toggleHealthcheck(int buildingId, int healthcheck_pending) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "UPDATE polygon.building SET healthcheck_pending=? WHERE building_id=?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, healthcheck_pending);
            stmt.setInt(2, buildingId);
            //Execute update.
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.toggleHealthcheck:"+ e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.toggleHealthcheck." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to reassign a healthcheck from status pending on to a technician
     *
     * @param buildingId int specifying which building for which the healthcheck
     * needs reassigning
     * @param technicianId int detailing which technician should be assigned the
     * healthcheck
     * @throws PolygonException
     */
    @Override
    public void assignHealthcheck(int buildingId, int technicianId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "UPDATE polygon.building SET assigned_tech_id=?, healthcheck_pending=? WHERE building_id=?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, technicianId);
            stmt.setInt(2, 2);
            stmt.setInt(3, buildingId);
            //Execute update.
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.assignHealthcheck:" + e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.assignHealthcheck." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to accept a healthcheck assigned to a technician
     *
     * @param buildingId int specifying which building is accepted by the
     * technician
     * @param technicianId int detailing which technician is accepting the
     * healthcheck
     * @throws PolygonException
     */
    @Override
    public void acceptHealthcheck(int buildingId, int technicianId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "UPDATE polygon.building SET healthcheck_pending=? WHERE building_id=?; ALTER TABLE polygon.healthcheck AUTO_INCREMENT=1;INSERT INTO polygon.healthcheck (tech_id, building_id) VALUES (?, ?);";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, 3);
            stmt.setInt(2, buildingId);
            stmt.setInt(3, technicianId);
            stmt.setInt(4, buildingId);
            //Execute update.
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.acceptHealthCheck:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper..acceptHealthcheck." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to create an issue
     *
     * @param buildingId int specifying which building the issue is created in
     * @param areaId int specifying which area the issue is created in
     * @param roomId int specifying which room the issue is created in
     * @param description String containing the description of the issue
     * @param recommendation String containing a recommendation from the
     * technician on how to fix issue
     * @param healthcheck_id Int specifying the healthcheck that is linked to
     * the issue
     * @return generated id of the inserted key.
     * @throws PolygonException
     */
    @Override
    public int createIssue(int buildingId, int areaId, int roomId, String description, String recommendation, int healthcheck_id) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "";
            
            if (roomId == 0) {
                
                sql = "INSERT INTO polygon.issue (description, recommendation, building_id, area_id, room_id, healthcheck_id) VALUES (?, ?, ?, ?, NULL, ?)";
            
            } else if (roomId != 0) {
                
                sql = "INSERT INTO polygon.issue (description, recommendation, building_id, area_id, room_id, healthcheck_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            }
            
            //Creating prepare statement.
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, description);
            stmt.setString(2, recommendation);
            stmt.setInt(3, buildingId);
            stmt.setInt(4, areaId);
            
            if (roomId != 0) {
                
                stmt.setInt(5, roomId);
                stmt.setInt(6, healthcheck_id);
                
            } else if (roomId == 0) {
                
                stmt.setInt(5, healthcheck_id);
                
            }

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                return rs.getInt(1);

            } else {

                return 0;

            }

        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.createIssue:" + e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("Exception@BuildingMapper.acceptHealthcheck." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to delete an issue
     *
     * @param issueId int specifying which issue is to be deleted
     * @throws PolygonException
     */
    @Override
    public void deleteIssue(int issueId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "DELETE FROM issue WHERE issue_id = ?;ALTER TABLE issue AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, issueId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.deleteIssue:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.deleteIssue." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to get a specific buildings healthchecks
     *
     * @param buildingId Int specifying the building
     * @return An ArrayList of type Healthcheck
     * @throws PolygonException
     */
    @Override
    public ArrayList<Healthcheck> getBuildingHealthchecks(int buildingId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.healthcheck WHERE building_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                //Add current healthcheck from RS into the buildingHealthchecks-ArrayList.
                buildingHealthchecks.add(new Healthcheck(rs.getInt(1), rs.getTimestamp(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
            
            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getBuildingHealthchecks:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //Throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getBuildingHealthchecks." + e.getMessage());

            }

        }

        return buildingHealthchecks;
        
    }

    /**
     * Method to get all healthchecks
     *
     * @return an arraylist of type healthcheck
     * @throws PolygonException
     */
    @Override
    public ArrayList<Healthcheck> getAllHealthchecks() throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.healthcheck";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                //Add current healthcheck from RS into the buildingHealthchecks-ArrayList.
                allHealthchecks.add(new Healthcheck(rs.getInt(1), rs.getTimestamp(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
            
            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getAllHealthchecks:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.@DBFacade.getAllHealthchecks:" + e.getMessage());

            }

        }

        return allHealthchecks;
        
    }

    /**
     * Method to get issues from a specific healthcheck
     *
     * @param healthcheckId Int specifying the healthcheck
     * @return an Arraylist of the type issue
     * @throws PolygonException
     */
    @Override
    public ArrayList<Issue> getHealthcheckIssues(int healthcheckId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.issue WHERE healthcheck_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, healthcheckId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                //Add current healthcheck from RS into the allHealthchecks-ArrayList.
                healthcheckIssues.add(new Issue(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));
            
            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getHealthcheckIssues:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getHealthcheckIssues." + e.getMessage());

            }

        }

        return healthcheckIssues;
        
    }

    /**
     * Method to register a poisture measurement to a room
     *
     * @param roomId Int specifying the room
     * @param measurePoint String specifying where in the room it was measured
     * @param measureValue int specifying the value of the measurement for
     * polygon to evaluate
     * @throws PolygonException
     */
    @Override
    public void registerMoistureMeasurement(int roomId, String measurePoint, int measureValue) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "INSERT INTO polygon.moisture_info (measure_point, moisture_value, room_id) VALUES (?, ?, ?)";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, measurePoint);
            stmt.setInt(2, measureValue);
            stmt.setInt(3, roomId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.registerMoistureMeasurement:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.registerMoistureMeasurement:" + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to get all moisture measurements
     *
     * @return an Arraylist of the type moisture measurements
     * @throws PolygonException
     */
    @Override
    public ArrayList<MoistureInfo> getAllMoistureMeasurements() throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.moisture_info";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {
                
                //Add current moisture measurement from RS into the buildingHealthchecks-ArrayList.
                allMoistureMeasurements.add(new MoistureInfo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                
            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getAllMoistureMeasurements:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //Throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getAllMoistureMeasurements" + e.getMessage());

            }

        }

        return allMoistureMeasurements;
        
    }

    /**
     * Method to delete a moisture measurement
     *
     * @param moistId Int specifying what id is to be deleted.
     * @throws PolygonException
     */
    @Override
    public void deleteMoistureMeasurement(int moistId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "DELETE FROM moisture_info WHERE moisture_info_id = ?;ALTER TABLE moisture_info AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, moistId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.deleteMoistureMeasurement:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.deleteMoistureMeasurement." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to get all damage repairs
     *
     * @return an Ararylist of the type damage repairs
     * @throws PolygonException
     */
    @Override
    public ArrayList<DamageRepair> getAllDamageRepairs() throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.damage_repair";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Declare and instantiate type.
            DamageRepair.type type;

            //Loop through the resultSet.
            while (rs.next()) {

                //If-condition, checking the condition of the damage_repair.
                //The local varible gets assigned the ENUM from the rs.
                if (rs.getString(7).equals(DamageRepair.type.DAMP.toString())) {

                    type = DamageRepair.type.DAMP;

                } else if (rs.getString(7).equals(DamageRepair.type.ROTFUNGUS.toString())) {

                    type = DamageRepair.type.ROTFUNGUS;

                } else if (rs.getString(7).equals(DamageRepair.type.MOULD.toString())) {

                    type = DamageRepair.type.MOULD;

                } else if (rs.getString(7).equals(DamageRepair.type.FIRE.toString())) {

                    type = DamageRepair.type.FIRE;
                } else {

                    type = DamageRepair.type.OTHER;

                }

                //Add current moisture measurement from RS into the buildingHealthchecks-ArrayList.
                allDamageRepairs.add(new DamageRepair(rs.getInt(1), rs.getBoolean(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5), rs.getString(6), type, rs.getInt(8)));
            
            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getAllDamageRepairs:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.getAllDamageRepairs:" + e.getMessage());

            }

        }

        return allDamageRepairs;
        
    }

    /**
     * Method to register a damage repairs
     *
     * @param roomId Int specifying the room
     * @param damageTime String specifying the time the damage occured
     * @param damageLocation String specifying the damage location
     * @param damageDetails String specifying the details of the damage
     * @param workDone String specifying what have been repaired
     * @param type String specifying the kind of damage
     * @throws PolygonException
     */
    @Override
    public void registerDamageRepair(int roomId, String damageTime, String damageLocation, String damageDetails, String workDone, String type) throws PolygonException {
        
//Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //String sql = "UPDATE polygon.building SET postcode=? WHERE building_id=?";
            String sql = "INSERT INTO polygon.damage_repair (previous_damage, date_occurred, location, details, work_done, type, room_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setTimestamp(2, timestamp);
            stmt.setString(3, damageLocation);
            stmt.setString(4, damageDetails);
            stmt.setString(5, workDone);
            stmt.setString(6, type);
            stmt.setInt(7, roomId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.registerDamageRepiar:"+ e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.registerDamageRepair." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to delete a damage repair report
     *
     * @param roomId Int specifying the room
     * @throws PolygonException
     */
    @Override
    public void deleteDamageRepair(int roomId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "DELETE FROM damage_repair WHERE room_id = ?;ALTER TABLE damage_repair AUTO_INCREMENT=1;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomId);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception@BuildingMapper.deleteDamageRepair:"+e.getMessage());
            
        } finally {
            
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.deleteDamageRepair." + e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method to complete a healthcheck
     *
     * @param condition String specifying the condition of the building
     * @param buildingResponsible String specifying the technician who created
     * the healthcheck
     * @param healthcheckId Int specifying the healthcheck
     * @param buildingId Int specifying the building
     * @throws PolygonException
     */
    @Override
    public void completeHealthcheck(String condition, String buildingResponsible, int healthcheckId, int buildingId) throws PolygonException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            String sql = "UPDATE `polygon`.`building` SET `condition`= ?, `healthcheck_pending`='0', `assigned_tech_id` = ? WHERE `building_id`= ?; UPDATE `polygon`.`healthcheck` SET `building_responsible`= ? WHERE `healthcheck_id`= ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            stmt.setString(1, condition);
            stmt.setNull(2, Types.INTEGER);
            stmt.setInt(3, buildingId);
            stmt.setString(4, buildingResponsible);
            stmt.setInt(5, healthcheckId);

            //Execute update.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.completeHealthcheck"+e.getMessage());

        } finally {
            
            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException e) {
                
                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper.completeHealthcheck:"+e.getMessage());
                
            }
            
        }
        
    }

    /**
     * Method used to get a specific building
     *
     * @param buildingId int specifying what building need to be retrieved
     * @return one specific building
     * @throws Exception
     */
    @Override
    public Building getBuilding(int buildingId) throws PolygonException {

        Building building = new Building();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM building WHERE building_id = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            if (rs.next()) {

                //If-condition, checking the condition of the building.
                //The local varible gets assigned the ENUM from the rs.
                if (rs.getString(7).equals(Building.condition.GOOD.toString())) {

                    building.setCondition(Building.condition.GOOD);

                } else if (rs.getString(7).equals(Building.condition.MEDIUM.toString())) {

                    building.setCondition(Building.condition.MEDIUM);

                } else if (rs.getString(7).equals(Building.condition.POOR.toString())) {

                    building.setCondition(Building.condition.POOR);

                } else {

                    building.setCondition(Building.condition.NONE);

                }

                building.setbuildingId(rs.getInt(1));
                building.setName(rs.getString(2));
                building.setDate_created(rs.getTimestamp(3));
                building.setAddress(rs.getString(4));
                building.setPostcode(rs.getInt(5));
                building.setCity(rs.getString(6));
                building.setConstruction_year(rs.getInt(8));
                building.setPurpose(rs.getString(9));
                building.setSqm(rs.getInt(10));
                building.setHealthcheck_pending(rs.getInt(11));
                building.setUser_id(rs.getInt(12));

            }

        } catch (Exception e) {

            throw new PolygonException("Exception@BuildingMapper.getBuilding:"+e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException e) {

                //throw error if not successful. 
                throw new PolygonException("SQLException@BuildingMapper:"+e.getMessage());

            }

        }

        //Return building.
        return building;

    }
    
}

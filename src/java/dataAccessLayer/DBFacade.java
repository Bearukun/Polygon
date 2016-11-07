package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import serviceLayer.entities.Building;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {

    //Declare and instantiate ArrayLists.
    private ArrayList<Building> userBuilding = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<User> allUsers = new ArrayList();

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
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        //Return ArrayList of Building(s).
        return userBuilding;
    }

    @Override
    public User getUser(String email) throws CustomException {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM user WHERE email = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setString(1, email);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //If-statement checking what kind of user is loggin in. 
            if (rs.next()) {
                
                //Declare and instantiate type.
                User.type type;

                if (rs.getString(4).equals(User.type.ADMIN.toString())) {
                    
                    //If admin.
                    type = User.type.ADMIN;
                    
                } else if (rs.getString(4).equals(User.type.TECHNICIAN.toString())) {
                    
                    //If tech.
                    type = User.type.TECHNICIAN;

                } else {
                    
                    //If cust.
                    type = User.type.CUSTOMER;
                    
                }
                
                //User(int user_id, String email, String password, type type, String name, int phone, String company, String address, int postcode, String city)
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), type, rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10));

            } else {
                
                throw new CustomException("BDFacade:@getUser-No match for passwd and email.");
                
            }
            
        } catch (Exception e) {
            
            throw new CustomException("SQL Error: Database connection failed.");
            
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

   
    /**
     * Method used to create a new user.
     * @param email
     * @param password
     * @param name
     * @param phone
     * @param company
     * @param address
     * @param postcode
     * @param city
     * @throws CustomException
     */
    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {

            //We need to check wether user already exists. 
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM user WHERE email = ?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setString(1, email);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //If there isn't anything in the ResultSet.
            if (!rs.next()) {

                //Prepare statement, notice that we don't need to specify 'type' here, hence
                //default type is CUSTOMER. 
                sql = "INSERT INTO `polygon`.`user` (`email`, `password`, `name`, `phone`, `company`, `address`, `postcode`, `city`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                //Creating prepare statement.
                stmt = con.prepareStatement(sql);
                //Insert variables if into prepareStatement.
                stmt.setString(1, email);
                stmt.setString(2, password);
                stmt.setString(3, name);
                stmt.setInt(4, phone);
                stmt.setString(5, company);
                stmt.setString(6, address);
                stmt.setInt(7, postcode);
                stmt.setString(8, city);
                //Execute update
                stmt.executeUpdate();

            } else {

                //If the ResultSet returns positive on email.
                throw new CustomException("Email in use.");

            }

        } catch (Exception e) {

            throw new CustomException("SQL Error: Email in use.");

        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

    }

    @Override
    public void createBuilding(String name, String address, Integer postcode, String city, Integer construction_year, String purpose, Integer sqm, int user_id ) throws CustomException {

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
            
            //Currently disabled due to Custopmexception being thrown, even when the SQL statment has been adjusted to:
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
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

    }

    @Override
    public ArrayList<User> getUsers() throws CustomException {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.user";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            //Loop through the resultSet.
            while (rs.next()) {

                //Declare and instantiate type.
                User.type type;
                
                //If-condition, checking the condition of the building.
                //The local varible gets assigned the ENUM from the rs.
                if (rs.getString(4).equals(User.type.ADMIN.toString())) {
                    
                    type = User.type.ADMIN;
                    
                } else if (rs.getString(4).equals(User.type.TECHNICIAN.toString())) {
                    
                    type = User.type.TECHNICIAN;

                } else {
                    
                    type = User.type.CUSTOMER;
                    
                }

                allUsers.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), type, rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
            
            }
        } catch (Exception e) {
            
            throw new CustomException("SQL Error: getUsers failed in facade.");
            
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        return allUsers;
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
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        return allBuildings;

    }

    /**
     * Method to update the details of a building
     * @throws CustomException
     */
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
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

    @Override
    public void editUser(int selectedUser, String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city) throws CustomException {
        
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        
            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "UPDATE polygon.user SET email=?, password=?, name=?, phone=?, company=?, address=?, postcode=?, city=? WHERE user_id=?";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert variables if into prepareStatement.
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setInt(4, phone);
            stmt.setString(5, company);
            stmt.setString(6, address);
            stmt.setInt(7, postcode);
            stmt.setString(8, city);
            stmt.setInt(9, selectedUser);
            //Execute update
            stmt.executeUpdate();
            
        } catch (Exception e) {
            
            throw new CustomException("SQL Error: Connection problem.");
            
        }finally{
        
            //Try releasing objects. 
            try {
                
                con.close();
                stmt.close();
                
            } catch (SQLException ex) {
                
                //Trow error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

}

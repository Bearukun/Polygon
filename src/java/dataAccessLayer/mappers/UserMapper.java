package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.UserMapperInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

public class UserMapper implements UserMapperInterface {

    //Declare and instantiate ArrayLists.
    private ArrayList<User> allUsers = new ArrayList();

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
            String sql = "SELECT * FROM user WHERE email = ? SET NAMES utf8mb4";
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
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

    @Override
    public void createUser(String email, String password, String name, Integer phone, String company, String address, Integer postcode, String city, User.type type) throws CustomException {
        
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
                sql = "INSERT INTO `polygon`.`user` (`email`, `password`, `type`, `name`, `phone`, `company`, `address`, `postcode`, `city`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                //Creating prepare statement.
                stmt = con.prepareStatement(sql);
                //Insert variables if into prepareStatement.
                stmt.setString(1, email);
                stmt.setString(2, password);
                stmt.setString(3, type.toString());
                stmt.setString(4, name);
                stmt.setInt(5, phone);
                stmt.setString(6, company);
                stmt.setString(7, address);
                stmt.setInt(8, postcode);
                stmt.setString(9, city);
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
                
                //throw error if not sucessful. 
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
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }

        return allUsers;
        
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
                
                //throw error if not sucessful. 
                 throw new CustomException("SQL Error:@DBFacade.getBuildings."+ex.getMessage());
            
            }
            
        }
        
    }

}

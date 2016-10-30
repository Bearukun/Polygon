package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import serviceLayer.enties.Building;
import serviceLayer.enties.User;
import serviceLayer.exceptions.CustomException;

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {

    private ArrayList<Building> tempAL = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<User> tempUL = new ArrayList();

    @Override
    public ArrayList<Building> getBuildings(int user_id) throws CustomException {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM building WHERE user_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            int building_id, postcode, floor;
            String address, city, description;
            //rs.next();
            while (rs.next()) {
                building_id = rs.getInt(1);
                address = rs.getString(2);
                postcode = rs.getInt(3);
                city = rs.getString(4);
                floor = rs.getInt(6);
                description = rs.getString(7);
                
                tempAL.add(new Building(building_id, postcode, user_id, address, city, floor, description));
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error: Database connection failed.");
        }
//        if (tempAL.isEmpty()) {
//            throw new CustomException("No buildings available");
//        }
        return tempAL;
    }

    @Override
    public User getUser(String email) throws CustomException {

        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                User.type type;

                if (rs.getString(4).equals(User.type.ADMIN.toString())) {

                    type = User.type.ADMIN;

                } else if (rs.getString(4).equals(User.type.TECHNICIAN.toString())) {

                    type = User.type.TECHNICIAN;

                } else {

                    type = User.type.CUSTOMER;

                }
                //User(int user_id, String email, String password, type type)
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), type, rs.getString(5), rs.getInt(6));

            } else {
                throw new CustomException("No such user");
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error: Database connection failed.");
        }
    }

    //Creates new user 
    public void createUser(String email, String password) throws CustomException {

        try {
            System.out.println("test!");
            //We need to check wether user already exists. 
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            //If there isn't anything in the ResultSet.
            if (!rs.next()) {

                //Prepare statement, notice that we don't need to specify 'type' here, hence
                //default type is CUSTOMER. 
                sql = "INSERT INTO `polygon`.`user` (`email`, `password`) VALUES (?, ?);";

                stmt = con.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);

                stmt.executeUpdate();

            } else {

                //If the ResultSet returns positive on email.
                throw new CustomException("Email in use.");

            }

        } catch (Exception e) {

            throw new CustomException("SQL Error: Email in use.");

        }

    }

    @Override
    public void createBuilding(int user_id, String address, int postcode, String city, int floor, String description) throws CustomException {

        try {

            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO `polygon`.`building` (`address`, `postcode`, `city`, `user_id`) VALUES (?, ?, ?, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, address);
            stmt.setInt(2, postcode);
            stmt.setString(3, city);
            stmt.setInt(4, user_id);
            
            //Currently disabled due to Custopmexception being thrown, even when the SQL statment has been adjusted to:
            //"INSERT INTO `polygon`.`building` (`address`, `postcode`, `city`, `user_id`, 'floor', 'description') VALUES (?, ?, ?, ?, ?, ?);";
            //stmt.setInt(5, floor);
            //stmt.setString(6, description);

            stmt.executeUpdate();

        } catch (Exception e) {

            throw new CustomException("SQL Error: Connection problem.");

        }

    }

    @Override
    public ArrayList<User> getUsers() throws CustomException {
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM polygon.user";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();

            int user_id, phone;
            String email, password, type, name;
           
            
            
            //rs.next();
            while (rs.next()) {
                user_id = rs.getInt(1);
                email = rs.getString(2);
                password = rs.getString(3);
                type = rs.getString(4);
                name = rs.getString(5);
                phone = rs.getInt(6);
                
                tempUL.add(new User(user_id, email, password, User.type.valueOf(type), name, phone));
                
                
                
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error: Database connection failed.");
        }
//        if (tempAL.isEmpty()) {
//            throw new CustomException("No buildings available");
//        }
        return tempUL;
    }

    @Override
    public ArrayList<Building> getAllBuildings() throws CustomException {
        
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM polygon.building;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int building_id, postcode, user_id, floor;
            String address, city, description;
            //rs.next();
            while (rs.next()) {
                building_id = rs.getInt(1);
                address = rs.getString(2);
                postcode = rs.getInt(3);
                city = rs.getString(4);
                user_id = rs.getInt(5);
                floor = rs.getInt(6);
                description = rs.getString(7);
                allBuildings.add(new Building(building_id, postcode, user_id, address, city, floor, description));
            }
        } catch (Exception e) {
            throw new CustomException("SQL Error: Database connection failed.");
        }
//        if (tempAL.isEmpty()) {
//            throw new CustomException("No buildings available");
//        }
        return allBuildings;
        
    }

}

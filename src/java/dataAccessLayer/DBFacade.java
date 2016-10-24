package dataAccessLayer;

import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serviceLayer.enties.User;
import serviceLayer.exceptions.CustomException;

/**
 * The purpose of DBFacade is to provide an encapsulated access to the database
 * (No SQL outside of the data-layer)
 */
public class DBFacade implements DBFacadeInterface {

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
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), type);

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

}

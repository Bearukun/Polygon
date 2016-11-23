package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serviceLayer.entities.Image;
import serviceLayer.exceptions.CustomException;

public class DataMapper implements DataMapperInterface {

    @Override
    public Image getImage(int image_id) throws Exception {

        Image img = new Image();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.image WHERE image_id = ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, image_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            if (rs.next()) {

                img.setImage_id(rs.getInt(1));
                img.setImg_name(rs.getString(2));
                img.setImg_file(rs.getBlob(3));

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.getImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new CustomException("SQL Error:@DataMapper.getImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    @Override
    public Image getBuildingImage(int building_id) throws Exception {

        Image img = new Image();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.image WHERE building_id = ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, building_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            if (rs.next()) {

                img.setImage_id(rs.getInt(1));
                img.setImg_name(rs.getString(2));
                img.setImg_file(rs.getBlob(3));
                img.setBuilding_id(rs.getInt(5));

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.getBuildingImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new CustomException("SQL Error:@DataMapper.getBuildingImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    @Override
    public Image getIssueImage(int issue_id) throws Exception {

        Image img = new Image();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.image WHERE issue_id = ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, issue_id);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            if (rs.next()) {

                img.setImage_id(rs.getInt(1));
                img.setImg_name(rs.getString(2));
                img.setImg_file(rs.getBlob(3));
                img.setIssue_id(rs.getInt(4));

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.getIssueImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new CustomException("SQL Error:@DataMapper.getIssueImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "INSERT INTO `polygon`.`image` (`img_name`, `img_file`, `issue_id`) VALUES (?, ?, ?);";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert data from parameter if into prepareStatement.
            stmt.setString(1, img_name);
            stmt.setBlob(2, img_file);
            stmt.setInt(3, issue_id);
            //Execute query.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.uploadIssueImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new CustomException("SQL Error:@DataMapper.uploadIssueImage." + ex.getMessage());

            }

        }

    }

    @Override
    public void uploadBuildingImage(int building_id, String img_name, InputStream img_file) throws Exception {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "INSERT INTO `polygon`.`image` (`img_name`, `img_file`, `building_id`) VALUES (?, ?, ?);";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert data from parameter if into prepareStatement.
            stmt.setString(1, img_name);
            stmt.setBlob(2, img_file);
            stmt.setInt(3, building_id);
            //Execute query.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.uploadBuildingImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new CustomException("SQL Error:@DataMapper.uploadBuildingImage." + ex.getMessage());

            }

        }

    }

}

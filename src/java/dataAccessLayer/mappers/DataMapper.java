package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import serviceLayer.entities.Image;
import serviceLayer.exceptions.CustomException;

public class DataMapper implements DataMapperInterface {

    @Override
    public Image getImage(int image_id) throws CustomException {

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
                img.setIssue_id(rs.getInt(4));
                
            }

        } catch (Exception e) {

            throw new CustomException("SQL Error:@DataMapper.getImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not sucessful. 
                throw new CustomException("SQL Error:@DataMapper.getImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

}

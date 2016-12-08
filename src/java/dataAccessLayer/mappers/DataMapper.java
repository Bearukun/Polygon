package dataAccessLayer.mappers;

import dataAccessLayer.DBConnection;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import serviceLayer.entities.Document;
import serviceLayer.entities.Image;

/**
 * Class dealing with file data
 */
public class DataMapper implements DataMapperInterface {

    /**
     * Method to retrieve an image
     *
     * @param image_id int specifying which image to retrieve
     * @return An object of type Image
     * @throws Exception
     */
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
                throw new Exception("SQL Error:@DataMapper.getImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    /**
     * Method to retrieve a building image
     *
     * @param buildingId int specifying which building's image to retrieve
     * @return An object of type Image
     * @throws Exception
     */
    @Override
    public Image getBuildingImage(int buildingId) throws Exception {

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
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            if (rs.next()) {

                img.setImage_id(rs.getInt(1));
                img.setImg_name(rs.getString(2));
                img.setImg_file(rs.getBlob(3));
                img.setbuildingId(rs.getInt(5));

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
                throw new Exception("SQL Error:@DataMapper.getBuildingImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    /**
     * Method to retrieve an issue's image
     *
     * @param issue_id int specifying which issue's image to retrieve
     * @return An object of type Image
     * @throws Exception
     */
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
                throw new Exception("SQL Error:@DataMapper.getIssueImage." + ex.getMessage());

            }

        }

        //Return image.
        return img;

    }

    /**
     * Method to upload an issue image
     *
     * @param issue_id int specifying which issue for which to upload an image
     * @param img_name String detailing the image name
     * @param img_file InputStream containing the image data
     * @throws Exception
     */
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
                throw new Exception("SQL Error:@DataMapper.uploadIssueImage." + ex.getMessage());

            }

        }

    }

    /**
     * Method to check whether a building already has an image
     *
     * @param imageType type specifying the type of the image
     * @param issue_id int containing the issue to be checked
     * @param buildingId int containing the building to be checked
     * @return true, if it has - false, if it does not.
     * @throws Exception
     */
    @Override
    public boolean hasImage(ImageType imageType, int issue_id, int buildingId) throws Exception {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            if (imageType == ImageType.building) {

                //Get connection object.
                con = DBConnection.getConnection();
                //Creating string used for the prepare statement.
                String sql = "SELECT * FROM polygon.image WHERE building_id = ?;";
                //Creating prepare statement.
                stmt = con.prepareStatement(sql);
                //Insert user if into prepareStatement.
                stmt.setInt(1, buildingId);
                //Execute query, and save the resultset in rs.
                rs = stmt.executeQuery();

            } else if (imageType == ImageType.issue) {

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

            }

            if (rs.next()) {

                //Note the finally will still run if the code returns here, neat java!
                return true;

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.hasImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.hasImage." + ex.getMessage());

            }

        }

        //Return false if given building id wasn't in the image-table. 
        return false;

    }

    /**
     * Method to upload a building image
     *
     * @param buildingId int specifying which building the image is pertaining
     * @param img_name String detailing the image name
     * @param img_file InputStream containing the image data
     * @throws Exception
     */
    @Override
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception {

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
            stmt.setInt(3, buildingId);
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
                throw new Exception("SQL Error:@DataMapper.uploadBuildingImage." + ex.getMessage());

            }

        }

    }

    /**
     * Method to update an existing image
     *
     * @param imageType type specifying the type of the image
     * @param issue_id Int specifying the image
     * @param buildingId Int specifying the building
     * @param img_name String specifying the name of the image
     * @param img_file InputStream specifying the image file
     * @throws Exception
     */
    @Override
    public void updateImage(ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws Exception {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            if (imageType == ImageType.building) {

                //Get connection object.
                con = DBConnection.getConnection();
                //Creating string used for the prepare statement.
                //UPDATE `polygon`.`image` SET `building_id`='7' WHERE `image_id`='5';
                String sql = "UPDATE `polygon`.`image` SET `img_name`=?, `img_file`=? WHERE `building_id`=?;";
                //Creating prepare statement.
                stmt = con.prepareStatement(sql);
                //Insert data from parameter if into prepareStatement.
                stmt.setString(1, img_name);
                stmt.setBlob(2, img_file);
                stmt.setInt(3, buildingId);
                //Execute query.
                stmt.executeUpdate();

            } else if (imageType == ImageType.issue) {

                //Get connection object.
                con = DBConnection.getConnection();
                //Creating string used for the prepare statement.
                String sql = "UPDATE `polygon`.`image` SET `img_name`=?, `img_file`=? WHERE `issue_id`=?;";
                //Creating prepare statement.
                stmt = con.prepareStatement(sql);
                //Insert data from parameter if into prepareStatement.
                stmt.setString(1, img_name);
                stmt.setBlob(2, img_file);
                stmt.setInt(3, issue_id);
                //Execute query.
                stmt.executeUpdate();

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.updateImage." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.updateImage." + ex.getMessage());

            }

        }

    }

    /**
     * Method to get decuments
     * 
     * @param buildingId int identifying the building the document is linked to
     * @return an Arraylist of documents
     * @throws Exception 
     */
    @Override
    public ArrayList<Document> getDocuments(int buildingId) throws Exception {

        ArrayList<Document> documents = new ArrayList();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.document WHERE building_id = ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, buildingId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            while (rs.next()) {

                documents.add(new Document(rs.getInt(1), rs.getTimestamp(2), rs.getString(3), null, rs.getString(5), rs.getInt(6)));

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.getDocuments." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.getDocuments." + ex.getMessage());

            }

        }

        //Return image.
        return documents;
    }

    /**
     * Method to upload a document on a building
     * @param buildingId int identifying the building from the database
     * @param documentName String containing the documents name
     * @param documentType String containing the documents filetype
     * @param document_file InputStream containing the document file
     * @throws Exception 
     */
    @Override
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws Exception {

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "INSERT INTO `polygon`.`document` (`document_name`, `document_file`, `document_type`, `building_id`) VALUES (?, ?, ?, ?);";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert data from parameter if into prepareStatement.
            stmt.setString(1, documentName);
            stmt.setBlob(2, document_file);
            stmt.setString(3, documentType);
            stmt.setInt(4, buildingId);
            //Execute query.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.addDocument." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.uploadBuildingImage." + ex.getMessage());

            }

        }

    }

    /**
     * Method used to delete a document
     * @param documentId int identifying the document from the database
     * @throws Exception 
     */
    @Override
    public void deleteDocument(int documentId) throws Exception {
        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "DELETE FROM `polygon`.`document` WHERE `document_id`= ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert data from parameter if into prepareStatement.
            stmt.setInt(1, documentId);

            //Execute query.
            stmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.deleteDocument." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.deleteDocument." + ex.getMessage());

            }

        }

    }

    /**
     * Method used to get a specific document
     * @param documentId int identifying the document from the database
     * @return a specific document
     * @throws Exception 
     */
    @Override
    public Document getDocument(int documentId) throws Exception {
        Document document = new Document();

        //Declare new objects of the Connection and PrepareStatement.
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            //Get connection object.
            con = DBConnection.getConnection();
            //Creating string used for the prepare statement.
            String sql = "SELECT * FROM polygon.document WHERE document_id = ?;";
            //Creating prepare statement.
            stmt = con.prepareStatement(sql);
            //Insert user if into prepareStatement.
            stmt.setInt(1, documentId);
            //Execute query, and save the resultset in rs.
            rs = stmt.executeQuery();

            if (rs.next()) {

                document.setDocument_id(rs.getInt(1));
                document.setDate_created(rs.getTimestamp(2));
                document.setDocument_name(rs.getString(3));
                document.setDocument_file(rs.getBlob(4));
                document.setDocument_type(rs.getString(5));
                document.setBuilding_id(rs.getInt(6));

            }

        } catch (Exception e) {

            throw new Exception("SQL Error:@DataMapper.getDocument." + e.getMessage());

        } finally {

            //Try releasing objects. 
            try {

                con.close();
                stmt.close();
                rs.close();

            } catch (SQLException ex) {

                //throw error if not successful. 
                throw new Exception("SQL Error:@DataMapper.getDocument." + ex.getMessage());

            }

        }

        //Return document.
        return document;
    }

}

package dataAccessLayer.mappers.interfaces;

import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.entities.Document;
import serviceLayer.entities.Image;


public interface DataMapperInterface {

    /**
     * Enum used to show a buildings different conditions.
     */
    public enum ImageType {

        building, issue

    }

    public Image getImage(int image_id) throws Exception;

    public Image getBuildingImage(int buildingId) throws Exception;

    public Image getIssueImage(int issue_id) throws Exception;

    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;

    public boolean hasImage(ImageType imageType, int issue_id, int buildingId) throws Exception;

    public void updateImage(ImageType imageType, int issue_id, int buildingId, String img_name, InputStream img_file) throws Exception;

    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception;
    
    public ArrayList<Document> getDocuments(int buildingId) throws Exception;
    
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws Exception;
    
    public void deleteDocument(int documentId) throws Exception;

    public Document getDocument(int documentId) throws Exception;
    
}

package serviceLayer.controllers.interfaces;

import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.entities.Document;
import serviceLayer.entities.Image;

public interface DataControllerInterface {

    Image getImage(int image_id) throws Exception;
    
    public Image getBuildingImage(int buildingId) throws Exception;

    public Image getIssueImage(int issue_id) throws Exception;

    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;

    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception;
    
    Image checkIfImageExists(Image img)throws Exception;
    
    public ArrayList<Document> getDocuments(int buildingId) throws Exception;
    
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws Exception;
    
    public void deleteDocument(int documentId) throws Exception;
    
    public Document getDocument(int documentId) throws Exception;

}

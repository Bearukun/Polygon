package serviceLayer.controllers.interfaces;

import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.entities.Document;
import serviceLayer.entities.Image;
import serviceLayer.exceptions.PolygonException;

public interface DataControllerInterface {

    Image getImage(int image_id) throws PolygonException;
    
    public Image getBuildingImage(int buildingId) throws PolygonException;

    public Image getIssueImage(int issue_id) throws PolygonException;

    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws PolygonException;

    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws PolygonException;
    
    Image checkIfImageExists(Image img)throws PolygonException;
    
    public ArrayList<Document> getDocuments(int buildingId) throws PolygonException;
    
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws PolygonException;
    
    public void deleteDocument(int documentId) throws PolygonException;
    
    public Document getDocument(int documentId) throws PolygonException;
    

}

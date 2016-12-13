package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import dataAccessLayer.mappers.interfaces.DataMapperInterface;
import java.io.InputStream;
import java.util.ArrayList;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.entities.Document;
import serviceLayer.entities.Image;
import serviceLayer.exceptions.PolygonException;

public class DataController implements DataControllerInterface {

    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public Image getImage(int image_id) throws PolygonException {

        return checkIfImageExists(dbfacade.getImage(image_id));

    }

    @Override
    public Image getBuildingImage(int buildingId) throws PolygonException {

        return checkIfImageExists(dbfacade.getBuildingImage(buildingId));

    }

    @Override
    public Image getIssueImage(int issue_id) throws PolygonException {

        return checkIfImageExists(dbfacade.getIssueImage(issue_id));

    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws PolygonException {

        if (dbfacade.hasImage(DataMapperInterface.ImageType.issue, issue_id, 0)) {

            dbfacade.updateImage(DataMapperInterface.ImageType.issue, issue_id, 0, img_name, img_file);

        } else {

            dbfacade.uploadIssueImage(issue_id, img_name, img_file);

        }


    }

    @Override
    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws PolygonException {

        if (dbfacade.hasImage(DataMapperInterface.ImageType.building, 0, buildingId)) {

            dbfacade.updateImage(DataMapperInterface.ImageType.building, 0, buildingId, img_name, img_file);

        } else {

            dbfacade.uploadBuildingImage(buildingId, img_name, img_file);

        }

    }

    /**
     * Method used to return the default image if the requested img does not
     * exist.
     *
     * @param img The Image to be examined.
     * @return returns either a existing image, or the default.
     * @throws PolygonException throws the exception
     */
    @Override
    public Image checkIfImageExists(Image img) throws PolygonException {

        //If the image doesn't exist in the db, return the "default" img.
        if (img.getImg_file() == null) {

            return img = dbfacade.getImage(1);

        } else {

            return img;

        }

    }

    @Override
    public ArrayList<Document> getDocuments(int buildingId) throws PolygonException {
        
        return dbfacade.getDocuments(buildingId);
        
    }

    @Override
    public void uploadDocument(int buildingId, String documentName, String documentType, InputStream document_file) throws PolygonException {
        
        dbfacade.uploadDocument(buildingId, documentName, documentType, document_file);
        
    }

    @Override
    public void deleteDocument(int documentId) throws PolygonException {
        
        dbfacade.deleteDocument(documentId);
        
    }

    @Override
    public Document getDocument(int documentId) throws PolygonException {
        
        return dbfacade.getDocument(documentId);
        
    }

}

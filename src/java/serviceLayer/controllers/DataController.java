package serviceLayer.controllers;

import dataAccessLayer.DBFacade;
import dataAccessLayer.interfaces.DBFacadeInterface;
import java.sql.Blob;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.entities.Image;

public class DataController implements DataControllerInterface {

    private final DBFacadeInterface dbfacade = new DBFacade();

    @Override
    public Image getImage(int image_id) throws Exception {

        return dbfacade.getImage(image_id);

    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, Blob img_file) throws Exception {

        dbfacade.uploadIssueImage(issue_id, img_name, img_file);

    }

    @Override
    public void uploadBuildingImage(int building_id, String img_name, Blob img_file) throws Exception {

        dbfacade.uploadBuildingImage(building_id, img_name, img_file);

    }

}

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

        return checkIfImageExists(dbfacade.getImage(image_id));

    }

    @Override
    public Image getBuildingImage(int building_id) throws Exception {

        return checkIfImageExists(dbfacade.getBuildingImage(building_id));

    }

    @Override
    public Image getIssueImage(int issue_id) throws Exception {

        return checkIfImageExists(dbfacade.getIssueImage(issue_id));

    }

    @Override
    public void uploadIssueImage(int issue_id, String img_name, Blob img_file) throws Exception {

        dbfacade.uploadIssueImage(issue_id, img_name, img_file);

    }

    @Override
    public void uploadBuildingImage(int building_id, String img_name, Blob img_file) throws Exception {

        dbfacade.uploadBuildingImage(building_id, img_name, img_file);

    }

    /**
     * Method used to return the default image if the requested img does not
     * exist.
     *
     * @param img The Image to be examined.
     * @return returns either a existing image, or the default.
     * @throws Exception
     */
    @Override
    public Image checkIfImageExists(Image img) throws Exception {

        if (img.getImg_file() == null) {

            return img = dbfacade.getImage(1);

        } else {

            return img;

        }

    }

}

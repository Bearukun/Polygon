package serviceLayer.controllers.interfaces;

import java.io.InputStream;
import serviceLayer.entities.Image;

public interface DataControllerInterface {

    Image getImage(int image_id) throws Exception;
    
    public Image getBuildingImage(int buildingId) throws Exception;

    public Image getIssueImage(int issue_id) throws Exception;

    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;

    public void uploadBuildingImage(int buildingId, String img_name, InputStream img_file) throws Exception;
    
    Image checkIfImageExists(Image img)throws Exception;

}

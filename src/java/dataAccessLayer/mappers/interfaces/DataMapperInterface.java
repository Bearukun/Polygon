package dataAccessLayer.mappers.interfaces;

import java.io.InputStream;
import java.sql.Blob;
import serviceLayer.entities.Image;

public interface DataMapperInterface {

    public Image getImage(int image_id) throws Exception;

    public Image getBuildingImage(int building_id) throws Exception;

    public Image getIssueImage(int issue_id) throws Exception;

    public void uploadIssueImage(int issue_id, String img_name, InputStream img_file) throws Exception;

    public void uploadBuildingImage(int building_id, String img_name, InputStream img_file) throws Exception;

}

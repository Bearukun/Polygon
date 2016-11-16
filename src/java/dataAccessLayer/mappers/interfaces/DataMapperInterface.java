package dataAccessLayer.mappers.interfaces;

import java.sql.Blob;
import serviceLayer.entities.Image;


public interface DataMapperInterface {

    public Image getImage(int image_id) throws Exception;
    
    public void uploadIssueImage(int issue_id, String img_name, Blob img_file) throws Exception;
    
    public void uploadBuildingImage(int building_id, String img_name, Blob img_file) throws Exception;
    
}

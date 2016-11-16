package dataAccessLayer.mappers.interfaces;

import java.sql.Blob;
import serviceLayer.entities.Image;


public interface DataMapperInterface {

    public Image getImage(int image_id) throws Exception;
    
    public void uploadIssueImage(int image_id, int issue_id, String img_name, Blob img_file) throws Exception;
    
}

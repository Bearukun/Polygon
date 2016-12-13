package serviceLayer.entities;
import java.io.Serializable;

import java.sql.Blob;

/**
 * 
 * Class used to hold an image.
 */

public class Image implements Serializable {
    
    /**
     * Variables corresponding to its database counterpart. 
     */
    private int image_id, issue_id, buildingId;
    private String img_name;
    private Blob img_file;

    /**
     * Empty constructor
     */
    public Image() {
    }

    /**
     * Constructor to instantiate a specific image
     * @param image_id int identifying the image from the database
     * @param issue_id int identifying the issue that is linked to the image
     * @param buildingId int identifying the building that is linked to the image
     * @param img_name String containing the images name
     * @param img_file Blob containing the file of the image
     */
    public Image(int image_id, int issue_id, int buildingId, String img_name, Blob img_file) {
        this.image_id = image_id;
        this.issue_id = issue_id;
        this.buildingId = buildingId;
        this.img_name = img_name;
        this.img_file = img_file;
    }


    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    public int getbuildingId() {
        return buildingId;
    }

    public void setbuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public Blob getImg_file() {
        return img_file;
    }

    public void setImg_file(Blob img_file) {
        this.img_file = img_file;
    }
    
    

    
    
}

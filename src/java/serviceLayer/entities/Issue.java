package serviceLayer.entities;

import java.io.Serializable;

/**
 * 
 * Class dealing with issues
 */
public class Issue implements Serializable {
    
    /**
     * Variables corresponding to its database counterpart. 
     */
    private int issue_id, buildingId, area_id, room_id, healthcheck_id;
    private String description, recommendation;
    
    /**
     * Empty constructor. 
     */
    public Issue() {
    }

    /**
     * Constructer to instantiate a specific issue, used when making a new issye and so on
     * @param issue_id int identifying the issue from the database
     * @param description String containing a description of the issue
     * @param recommendation String containing a recommendation from the technician
     * @param buildingId int identifying the building that is linked to the issue
     * @param area_id int identifying the area that is linked to the issye
     * @param room_id int identifying the room that is linked to the issue
     * @param healthcheck_id int identifying the healthcheck that is linked to the issue
     */
    public Issue(int issue_id, String description, String recommendation, int buildingId, int area_id, int room_id, int healthcheck_id) {
        this.issue_id = issue_id;
        this.description = description;
        this.recommendation = recommendation;
        this.buildingId = buildingId;
        this.area_id = area_id;
        this.room_id = room_id;
        this.healthcheck_id = healthcheck_id;
    }
    
    /**
     * Getters and setters (WE NEED TO CLEAN UP HERE BOYS) 
     */
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

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getHealthcheck_id() {
        return healthcheck_id;
    }

    public void setHealthcheck_id(int healthcheck_id) {
        this.healthcheck_id = healthcheck_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

}
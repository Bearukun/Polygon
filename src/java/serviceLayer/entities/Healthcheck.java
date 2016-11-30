package serviceLayer.entities;

import java.io.Serializable;
import java.sql.Timestamp;


public class Healthcheck {
    

    
    /**
     * Variables corresponding to its database counterpart. 
     */
    
    
    private int healthcheck_id, tech_id, buildingId;
    private String building_responsible;
    private Timestamp date_created;
    
    
    /**
     * Empty constructor. 
     */
    public Healthcheck() {
    }

    public Healthcheck(int healthcheck_id, Timestamp date_created, int tech_id, String building_responsible, int buildingId) {
        this.healthcheck_id = healthcheck_id;
        this.date_created = date_created;
        this.tech_id = tech_id;
        this.building_responsible = building_responsible;
        this.buildingId = buildingId;
    }

       /**
     * Getters and setters (WE NEED TO CLEAN UP HERE BOYS) 
     */
    
    public int getHealthcheck_id() {
        return healthcheck_id;
    }

    public void setHealthcheck_id(int healthcheck_id) {
        this.healthcheck_id = healthcheck_id;
    }

    public int getTech_id() {
        return tech_id;
    }

    public void setTech_id(int tech_id) {
        this.tech_id = tech_id;
    }

    public int getbuildingId() {
        return buildingId;
    }

    public void setbuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuilding_responsible() {
        return building_responsible;
    }

    public void setBuilding_responsible(String building_responsible) {
        this.building_responsible = building_responsible;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

}

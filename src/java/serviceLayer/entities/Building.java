package serviceLayer.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Class dealing with buildings
 */
public class Building implements Serializable {
    
    /**
     * Enum used to show a buildings different conditions.  
     */
    public enum condition {

        GOOD, MEDIUM, POOR, NONE

    }
    
    /**
     * Variables corresponding to its database counterpart. 
     */
    private int buildingId, postcode, construction_year, sqm, healthcheck_pending, user_id, assigned_tech_id;   
    private String name, address, city, purpose;
    private Timestamp date_created;
    private condition condition;
    
    
    /**
     * Empty constructor. 
     */
    public Building() {
    }

    /**
     * Constructor to instantiate a specific building, used when creating new buildings and so on
     * @param buildingId int identifying the building from the database
     * @param name String containing the buildings name
     * @param date_created timestamp containing the date the buildings was added to the database
     * @param address String containing the buildings address
     * @param postcode int containing the buildings postcode
     * @param city String containing the buildings city
     * @param condition type defining the buildings condition
     * @param construction_year int containing the buildings construction year
     * @param purpose String containing the buildings purpose
     * @param sqm int containing the buildings size in square meters
     * @param healthcheck_pending boolean saying if the building have a pending healthcheck or not
     * @param assigned_tech_id int containing the id of the technician thats assigned to the buildings healthcheck
     * @param user_id int identifying the user that is linked to the building
     */
    public Building(int buildingId, String name, Timestamp date_created, String address, int postcode, String city, condition condition, int construction_year, String purpose, int sqm, int healthcheck_pending, int assigned_tech_id, int user_id) {
        this.buildingId = buildingId;
        this.name = name;
        this.date_created = date_created;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.condition = condition;
        this.construction_year = construction_year;
        this.purpose = purpose;
        this.sqm  = sqm;   
        this.healthcheck_pending = healthcheck_pending;
        this.assigned_tech_id = assigned_tech_id;
        this.user_id = user_id;
    }

    
    /**
     * Getters and setters (WE NEED TO CLEAN UP HERE BOYS) 
     */
    
    public int getbuildingId() {
        return buildingId;
    }

    public void setbuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getConstruction_year() {
        return construction_year;
    }

    public void setConstruction_year(int construction_year) {
        this.construction_year = construction_year;
    }

    public int getSqm() {
        return sqm;
    }

    public void setSqm(int sqm) {
        this.sqm = sqm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public condition getCondition() {
        return condition;
    }

    public void setCondition(condition condition) {
        this.condition = condition;
    }

    public int getHealthcheck_pending() {
        return healthcheck_pending;
    }

    public void setHealthcheck_pending(int healthcheck_pending) {
        this.healthcheck_pending = healthcheck_pending;
    }

    public int getAssigned_tech_id() {
        return assigned_tech_id;
    }

    public void setAssigned_tech_id(int assigned_tech_id) {
        this.assigned_tech_id = assigned_tech_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

 

    
    
}

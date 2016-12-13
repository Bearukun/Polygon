package serviceLayer.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * Class used to hold a healthcheck
 */
public class Healthcheck implements Serializable {

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

    /**
     * Constructer to instantiate a specific healthcheck, used when making a new healthcheck and so on
     * @param healthcheck_id int identifying the healthcheck from the database
     * @param date_created timestamp containing the date the healthcheck was
     * added to the database
     * @param tech_id int identifying the technician that is linked to the
     * healthcheck
     * @param building_responsible id of building responsible
     * @param buildingId int identifying the building that is linked to the 
     * healthcheck
     */
    public Healthcheck(int healthcheck_id, Timestamp date_created, int tech_id, String building_responsible, int buildingId) {
        this.healthcheck_id = healthcheck_id;
        this.date_created = date_created;
        this.tech_id = tech_id;
        this.building_responsible = building_responsible;
        this.buildingId = buildingId;
    }


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

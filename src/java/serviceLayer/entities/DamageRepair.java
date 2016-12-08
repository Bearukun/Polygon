package serviceLayer.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * Class dealing with moisture scans
 */

public class DamageRepair implements Serializable {
    




    /**
     * Enum used to show a buildings different conditions.
     */
    public enum type {

        DAMP, ROTFUNGUS, MOULD, FIRE, OTHER
    }

    /**
     * Variables corresponding to its database counterpart.
     */
    private int damage_repair_id, roomId;
    private boolean previous_damage;
    private String location, details, work_done;
    private Timestamp date_occurred;
    private type type;

    /**
     * Empty constructor.
     */
    public DamageRepair() {
    }

    /**
     * Constructor to instantiate a specific repair report
     * 
     * @param damage_repair_id int specifying the repair report from the database
     * @param previous_damage boolean containing if there has been any previous damage
     * @param date_occurred Timastamp containing the time the report was created
     * @param location String containing the location of the damage
     * @param details String containing the details of the damage
     * @param work_done String containing the kind of work have been done
     * @param type type containing the type of damage
     * @param roomId int specifying the room that is linked to the repair report
     */
    public DamageRepair(int damage_repair_id, boolean previous_damage, Timestamp date_occurred, String location, String details, String work_done, type type, int roomId) {
        this.damage_repair_id = damage_repair_id;
        this.previous_damage = previous_damage;
        this.date_occurred = date_occurred;
        this.location = location;
        this.details = details;
        this.work_done = work_done;
        this.type = type;
        this.roomId = roomId;
    }

    /**
     * Getters and setters (WE NEED TO CLEAN UP HERE BOYS)
     */
    public int getDamage_repair_id() {
        return damage_repair_id;
    }

    public void setDamage_repair_id(int damage_repair_id) {
        this.damage_repair_id = damage_repair_id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isPrevious_damage() {
        return previous_damage;
    }

    public void setPrevious_damage(boolean previous_damage) {
        this.previous_damage = previous_damage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWork_done() {
        return work_done;
    }

    public void setWork_done(String work_done) {
        this.work_done = work_done;
    }

    public Timestamp getDate_occurred() {
        return date_occurred;
    }

    public void setDate_occurred(Timestamp date_occurred) {
        this.date_occurred = date_occurred;
    }

    public type getType() {
        return type;
    }

    public void setType(type type) {
        this.type = type;
    }

}

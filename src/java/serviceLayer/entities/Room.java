package serviceLayer.entities;
import java.io.Serializable;
/**
 * Class used to hold a room.
 */
public class Room implements Serializable {

    /**
     * Variables corresponding to its database counterpart.
     */
    private int roomId, sqm, moisture_scan, areaId;
    private String name, description;

    /**
     * Empty constructor.
     */
    public Room() {
    }

    /**
     * Constructor to instantiate a specific room
     * @param roomId int identifying the room from the database
     * @param name String containing the rooms name
     * @param description String containing the rooms description
     * @param sqm int containing the rooms size in square meters
     * @param moisture_scan int that can only be 0 or 1, where 0 is "false"
     * @param areaId int identifying the area that is linked to the room
     */
    public Room(int roomId, String name, String description, int sqm, int moisture_scan, int areaId) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.sqm = sqm;
        this.moisture_scan = moisture_scan;
        this.areaId = areaId;
    }


    public int getRoom_id() {
        return roomId;
    }

    public void setRoom_id(int roomId) {
        this.roomId = roomId;
    }

    public int getSqm() {
        return sqm;
    }

    public void setSqm(int sqm) {
        this.sqm = sqm;
    }

    public int getMoisture_scan() {
        return moisture_scan;
    }

    public void setMoisture_scan(int moisture_scan) {
        this.moisture_scan = moisture_scan;
    }

    public int getArea_id() {
        return areaId;
    }

    public void setArea_id(int areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  name  + " " +  description;
    }
    
}

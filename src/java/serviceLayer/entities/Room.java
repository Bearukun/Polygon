package serviceLayer.entities;

/**
 * Class dealing with rooms
 */
public class Room {

     /**
     * Variables corresponding to its database counterpart. 
     */
    private int room_id, sqm, moisture_scan, area_id;
    private String name, description;

    /**
     * Empty constructor. 
     */
    public Room() {
    }

    /**
     * Constructor to instantiate a specific room
     * @param room_id int identifying the room from the database
     * @param name String containing the rooms name
     * @param description String containing the rooms description
     * @param sqm int containing the rooms size in square meters
     * @param moisture_scan int that can only be 0 or 1, where 0 is "false"
     * @param area_id int identifying the area that is linked to the room
     */
    public Room(int room_id, String name, String description, int sqm, int moisture_scan, int area_id) {
        this.room_id = room_id;
        this.name = name;
        this.description = description;
        this.sqm = sqm;
        this.moisture_scan = moisture_scan;
        this.area_id = area_id;
    }

    /**
     *
     * @return getters & setters
     */
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
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
}

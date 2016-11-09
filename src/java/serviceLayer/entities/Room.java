package serviceLayer.entities;

public class Room {
    
    private int room_id, sqm, moisture_scan, area_id;   
    private String name, description;

    public Room() {
    }

    public Room(int room_id, String name, String description, int sqm, int moisture_scan, int area_id) {
        this.room_id = room_id;
        this.name = name;
        this.description = description;
        this.sqm = sqm;
        this.moisture_scan = moisture_scan;
        this.area_id = area_id;
    }

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

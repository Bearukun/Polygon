package serviceLayer.entities;

public class Room {
    
    private int roomId, sqm, moisture_scan, areaId;   
    private String name, description;

    public Room() {
    }

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
}

package serviceLayer.entities;

public class Area {
    
    private int areaId, sqm, buildingId;   
    private String name, description;

    public Area() {
    }

    public Area(int areaId, String name, String description, int sqm, int buildingId) {
        this.areaId = areaId;
        this.name = name;
        this.description = description;
        this.sqm = sqm;
        this.buildingId = buildingId;
    }

    public int getArea_id() {
        return areaId;
    }

    public void setArea_id(int areaId) {
        this.areaId = areaId;
    }

    public int getSqm() {
        return sqm;
    }

    public void setSqm(int sqm) {
        this.sqm = sqm;
    }

    public int getbuildingId() {
        return buildingId;
    }

    public void setbuildingId(int buildingId) {
        this.buildingId = buildingId;
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

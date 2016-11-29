package serviceLayer.entities;

public class Area {
    
    private int areaId, sqm, building_id;   
    private String name, description;

    public Area() {
    }

    public Area(int areaId, String name, String description, int sqm, int building_id) {
        this.areaId = areaId;
        this.name = name;
        this.description = description;
        this.sqm = sqm;
        this.building_id = building_id;
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

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
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

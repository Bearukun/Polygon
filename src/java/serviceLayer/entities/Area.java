package serviceLayer.entities;

/**
 * Class dealing with areas
 */
public class Area {

     /**
     * Variables corresponding to its database counterpart. 
     */
    private int area_id, sqm, building_id;
    private String name, description;

    /**
     * Empty constructor. 
     */
    public Area() {
    }

    /**
     * constructor to instantiate a specific area
     * @param area_id int identifying the area from the database
     * @param name String containing the areas name
     * @param description String containing the areas description
     * @param sqm int containing the areas size in square meters
     * @param building_id int identifying the building that is linked to the area
     */
    
    public Area(int area_id, String name, String description, int sqm, int building_id) {
        this.area_id = area_id;
        this.name = name; 
        this.description = description;
        this.sqm = sqm;
        this.building_id = building_id;
    }

    /**
     * 
     * @return getters & setters
     */
    
    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
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

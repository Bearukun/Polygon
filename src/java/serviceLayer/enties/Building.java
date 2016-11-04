
package serviceLayer.enties;

import java.sql.Timestamp;

public class Building {
    
    public enum condition {

        GOOD, MEDIUM, POOR, NONE

    }
    
    private int building_id, postcode, construction_year, sqm, user_id;   
    private String name, address, city, purpose;
    private Timestamp date_created;
    private condition condition;

    public Building() {
    }

    public Building(int building_id, String name, Timestamp date_created, String address, int postcode, String city, condition condition, int construction_year, String purpose, int sqm, int user_id) {
        this.building_id = building_id;
        this.name = name;
        this.date_created = date_created;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.condition = condition;
        this.construction_year = construction_year;
        this.purpose = purpose;
        this.sqm  = sqm;   
        this.user_id = user_id;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

 

    
    
}

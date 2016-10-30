
package serviceLayer.enties;

public class Building {
    
    private int building_id, postcode, user_id, floor;   
    private String address, city, description;

    public Building() {
    }

    public Building(int building_id, int postcode, int user_id, String address, String city, int floor, String description) {
        this.building_id = building_id;
        this.postcode = postcode;
        this.user_id = user_id;
        this.address = address;
        this.city = city;
        this.floor = floor;
        this.description = description;
        
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "Building: " + "building_id: " + building_id + ", postcode: " + postcode + ", user_id: " + user_id + ", address: " + address + ", city: " + city;
    }
    
    

    
    
         
    
}

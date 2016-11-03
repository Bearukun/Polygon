package serviceLayer.enties;

public class User {

    public enum type {

        CUSTOMER, TECHNICIAN, ADMIN

    }

    private int user_id, phone, postcode;
    private String email, password, name, company, address, city;
    private type type;
    



    public User(int user_id, String email) {
        this.user_id = user_id;
        this.email = email;
    }

    public User(int user_id, String password, int phone, int postcode, String email, , String name, String company, String address, String city, type type) {
        this.user_id = user_id;
        this.phone = phone;
        this.postcode = postcode;
        this.email = email;
        this.password = password;
        this.name = name;
        this.company = company;
        this.address = address;
        this.city = city;
        this.type = type;
    }
    
    

    
    
    

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public type getType() {
        return type;
    }

    public void setType(type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return email;
    }

    
}

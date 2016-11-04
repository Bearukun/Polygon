package serviceLayer.entities;

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

    public User(int user_id, String email, String password, type type, String name, int phone, String company, String address, int postcode, String city) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
    }

    public User() {
    }
    
    

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public type getType() {
        return type;
    }

    public void setType(type type) {
        this.type = type;
    }
    
    

    @Override
    public String toString() {
        return "User{" + "name=" + name + '}';
    }
    

    
}

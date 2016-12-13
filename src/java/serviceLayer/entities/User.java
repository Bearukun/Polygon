package serviceLayer.entities;

import java.io.Serializable;
/**
 * Class used to hold a user.
 */
public class User implements Serializable {

    /**
     * Enum used to show a users different types.
     */
    public enum type {

        CUSTOMER, TECHNICIAN, ADMIN

    }

    /**
     * Variables corresponding to its database counterpart.
     */
    private int user_id, phone, postcode;
    private String email, password, name, company, address, city;
    private type type;

    /**
     * Constructor used when logging in (NEED TO BE REMOVED WHEN NEW LOGIN HAS BEEN MADE)
     * @param user_id int identifying the user from the database
     * @param email String containing the users email
     */
    public User(int user_id, String email) {
        this.user_id = user_id;
        this.email = email;
    }

    /**
     * Constructor to instantiate a specific user, used when creating new users and so on
     * @param user_id int identifying the user from the database
     * @param email String containing the users email
     * @param password String containing the users password
     * @param type enum containing the type of the user
     * @param name String containing the users name
     * @param phone int containing the users phone number
     * @param company String containing the users conpany name
     * @param address String containing the companys address
     * @param postcode int containing the companys postcode
     * @param city String containing the companys city
     */
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

    /**
     * Empty constructor.
     */
    public User() {
    }

    /**
     * Constructor used in getTechnicians in the AdminServlet
     * @param user_id int identifying the user from the database
     * @param email String containing the users email
     * @param name String containing the users name
     */
    public User(int user_id, String email, String name) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
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

package serviceLayer.enties;

public class User {

    public enum type {

        CUSTOMER, TECHNICIAN, ADMIN

    }

    private int user_id;
    private String email, password;
    private type type;

    public User(int user_id, String email, String password, type type) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.type = type;
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

}

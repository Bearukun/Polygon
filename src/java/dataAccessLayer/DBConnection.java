package dataAccessLayer;

import static dataAccessLayer.ConnectionConstants.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = String.format("jdbc:mysql://%s:3306/%s?allowMultiQueries=true", HOST, DBNAME);
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(url, USER, USERPW);

    }

}

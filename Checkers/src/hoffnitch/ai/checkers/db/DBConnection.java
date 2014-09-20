package hoffnitch.ai.checkers.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private Connection c;
    
    public DBConnection() {
        openConnection();
    }
    
    private void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:checkers.db");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
    }
}

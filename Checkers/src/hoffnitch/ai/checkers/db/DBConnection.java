package hoffnitch.ai.checkers.db;

import hoffnitch.ai.checkers.Turn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBConnection {
    
    private Connection c;
    
    private static final String INSERT_GAME_PREFIX = "INSERT INTO Game (DarkPlayer,LightPlayer,Result,Moves,NumMoves) VALUES(";
    private static final String INSERT_GAME_SUFFIX = ");";
    
    private static final String DELIMITER = "$";
            
    
    public DBConnection() {
        openConnection();
    }
    
    private void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data/checkers.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void save(Game game) {
        try {
            Statement stmt = c.createStatement();
            StringBuilder addGame = new StringBuilder();
            addGame.append(INSERT_GAME_PREFIX);
            addGame.append("'" + game.getDarkPlayer() + "',");
            addGame.append("'" + game.getLightPlayer() + "',");
            addGame.append("'" + game.getResult() + "',");
            addGame.append("'" + delimitTurns(game.getTurns()) + "',");
            addGame.append(game.getNumMoves());
            addGame.append(INSERT_GAME_SUFFIX);
            
            stmt.executeUpdate(addGame.toString());
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private String delimitTurns(List<Turn> turns) {
        StringBuilder dbTurns = new StringBuilder();
        
        boolean first = true;
        for(Turn t : turns) {
            if (!first) {
                dbTurns.append(DELIMITER);
            }
            dbTurns.append(t.getRawList());
            first = false;
        }
        return dbTurns.toString();
    }
}

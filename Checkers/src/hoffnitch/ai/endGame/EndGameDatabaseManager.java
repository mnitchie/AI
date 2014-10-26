package hoffnitch.ai.endGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EndGameDatabase
{
	private static final String INSERT_PREFIX = "INSERT INTO endgame ("
							+ "pieceCount, indices, distance) "
							+ "VALUES (";
	private static final String INSERT_SUFFIX = ")";
	
	public static boolean createTable(Connection connection) {
		final String CREATE = "CREATE TABLE endgame("
				+ "pieceCount INT, "
				+ "indices INT, "
				+ "distance INT)";
		
		boolean success = false;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE);
			statement.close();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public static Connection getConnection() {
		final String SQLITE_NAME = "org.sqlite.JDBC";
		Connection connection = null;
		
		try {
			Class.forName(SQLITE_NAME);
			connection = DriverManager.getConnection("jdbc:sqlite:data/endGameDatabase.db");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean insertGameState(Connection connection, CondensedGameState gameState, long distance) {
		boolean success = false;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(INSERT_PREFIX
					+ gameState.pieceCounts + ","
					+ gameState.indices + ","
					+ distance
					+ INSERT_SUFFIX);
			statement.close();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
}

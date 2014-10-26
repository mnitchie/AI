package hoffnitch.ai.endGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EndGameDatabaseManager
{
	private static final String INSERT_PREFIX = "INSERT INTO endgame ("
							+ "pieceCount, indices, distance) "
							+ "VALUES (";
	private static final String INSERT_SUFFIX = ")";
	private static final String SELECT_PREFIX = "SELECT distance FROM endgame WHERE ";
	private static final String PIECE_COUNT_EQUALS = "pieceCount=";
	private static final String AND = " AND ";
	private static final String INDICES_EQUALS = "indices=";
	private static final String SELECT_SUFFIX = ")";
	
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
			// no big deal, it's already there.
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
	
	public static boolean insertGameState(Connection connection, CondensedGameState gameState, int distance) {
		return insertGameState(connection, gameState.pieceCounts, gameState.indices, distance);
	}
	
	public static boolean insertGameState(Connection connection, long pieceCount, long indices, int distance) {
		
		boolean success = false;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(INSERT_PREFIX
					+ pieceCount + ","
					+ indices + ","
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
	
	public static int selectGameState(Connection connection, long pieceCount, long indices) {
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(SELECT_PREFIX
					+ PIECE_COUNT_EQUALS + pieceCount
					+ AND
					+ INDICES_EQUALS + indices
					);
			if (result.next()) {
				return result.getInt("distance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}

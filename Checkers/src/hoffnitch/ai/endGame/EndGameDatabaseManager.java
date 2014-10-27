package hoffnitch.ai.endGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
	private static final String INSERT = "INSERT INTO endgame(pieceCount, indices, distance) VALUES(?, ?, ?)";
	private static final String LIMIT_ONE = " LIMIT 1";
	
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
	
	public static boolean hasPieceCount(long pieceCount) {
		boolean hasPieceCount = false;
		
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(SELECT_PREFIX
					+ PIECE_COUNT_EQUALS
					+ pieceCount
					+ LIMIT_ONE);
			
			if (result.next()) {
				hasPieceCount = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasPieceCount;
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
	
	public static void batchInsertGameStates(Connection connection, HashMap<Long, HashMap<Long, Integer>> endGameScenarios, List<Long> pieceCounts) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.addBatch("BEGIN");
			for (Long pieceCount: pieceCounts) {
				HashMap<Long, Integer> endGames = endGameScenarios.get(pieceCount);
				Set<Long> indexSets = endGames.keySet();
				for (Long indexSet: indexSets) {
					Integer distance = endGames.get(indexSet);
					String query = INSERT_PREFIX
							+ pieceCount + ","
							+ indexSet + ","
							+ distance
							+ INSERT_SUFFIX
							;
					statement.addBatch(query);
					
				}
			}
			statement.addBatch("END");
			statement.executeBatch();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int selectGameState(Statement statement, Connection connection, long pieceCount, long indices) {
		try {
			
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
	
	public static int selectGameState(Connection connection, long pieceCount, long indices) {
		try {
			Statement statement = connection.createStatement();
			statement.close();
			return selectGameState(statement, connection, pieceCount, indices);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}

package hoffnitch.ai.endGame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class EndGameData implements Runnable
{
	private HashMap<Long, HashMap<Long, Integer>> endGameScenarios;
	private volatile boolean isLoaded = false;
	
	public EndGameData() {
		endGameScenarios = new HashMap<Long, HashMap<Long, Integer>>();
		(new Thread(this)).start();
	}
	
	@Override
	public void run() {
		try {

			Connection connection = EndGameDatabaseManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet allData = statement.executeQuery("SELECT * FROM endgame");
			System.out.println("loading endgame data");
			
			int distance;
			long pieceCount;
			long indices;
			int numLoaded = 0;
			HashMap<Long, Integer> mapForPieceCount;
			while (allData.next()) {
				distance = allData.getInt("distance");
				pieceCount = allData.getLong("pieceCount");
				indices = allData.getLong("indices");
				
				mapForPieceCount = endGameScenarios.get(pieceCount);
				if (mapForPieceCount == null) {
					mapForPieceCount = new HashMap<Long, Integer>();
					endGameScenarios.put(pieceCount, mapForPieceCount);
				}
				
				mapForPieceCount.put(indices, distance);
				numLoaded++;
			}
			System.out.println("endgame data loaded");
			isLoaded = true;
			
			
		} catch (SQLException e) {
			System.out.println("failed to load endgame database");
		}
	}

	public HashMap<Long, HashMap<Long, Integer>> getEndGameScenarios() {
		return endGameScenarios;
	}

	public boolean isLoaded() {
		return isLoaded;
	}
	
}

package hoffnitch.ai.checkers.boardSetup;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;

public class ScenarioInitializer extends BoardInitializer {

	private CheckersScenario scenario;
	
	public ScenarioInitializer(CheckersScenario scenario) {
		setScenario(scenario);
	}
	
	public void setScenario(CheckersScenario scenario) {
		this.scenario = scenario;
	}

	@Override
	public void setBoard(GameState board)
	{
		clearBoard(board);

	    for (Piece p : scenario.getScenario())
	        board.setPiece(p, p.getPosition());		
	}
	
}

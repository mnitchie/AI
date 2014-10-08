package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public class RatioBot extends AIPlayer
{
	public static final String HEURISTIC_DESCRIPTION = "Count Bot";
	
	public RatioBot(PieceColor color) {
		super(HEURISTIC_DESCRIPTION, color);
	}
	
	@Override
	public Turn getTurn(List<Turn> options) {
		int random = (int)Math.floor(Math.random() * options.size());
		return options.get(random);
	}
	
	@Override
	public double evaluateBoard(GameState board) {
	    return getRatioWeight() * scoreBoardOnPieceRatio(board);
	}
}

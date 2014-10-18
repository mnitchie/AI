package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class CountBot extends AIPlayer
{
	public static final String HEURISTIC_DESCRIPTION = "Count Bot";
	private double kingWeight;
	
	public CountBot(PieceColor color, double kingWeight) {
		super(HEURISTIC_DESCRIPTION, color);
		this.kingWeight = kingWeight;
	}

	@Override
	public double evaluateBoard(GameState board) {
		return scoreBoardOnPieceCount(board, kingWeight);
	}

}

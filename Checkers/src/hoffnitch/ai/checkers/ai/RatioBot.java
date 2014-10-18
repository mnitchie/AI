package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class RatioBot extends AIPlayer
{
	public static final String HEURISTIC_DESCRIPTION = "Ratio Bot";
	private double kingWeight;
	
	public RatioBot(PieceColor color, double kingWeight) {
		super(HEURISTIC_DESCRIPTION, color);
		this.kingWeight = kingWeight;
	}
	
	@Override
	public double evaluateBoard(GameState board) {
		return scoreBoardOnWeightedRatio(board, kingWeight);
	}
}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;

public class CornerDefender extends AIPlayer {
	public static final String HEURISTIC_DESCRIPTION = "Corner Defender";
	
	private double promotionWeight = 30.0;
	
	public CornerDefender(PieceColor color) {
		super(HEURISTIC_DESCRIPTION, color, 1000.0, 17.0, 2.0);
		setPawnWeight(15.0);
	}
	
	@Override
	public double evaluateBoard(GameState board)
	{
		double ratio = getRatioWeight() * scoreBoardOnWeightedRatio(board);
		double output;
		if (ratio == 1 || ratio == 0) {
			output = ratio;
		} else {
			double promotion = promotionWeight * scoreBoardOnDistanceToPromotion(board);
			double kingDistance = 5.0 * scoreBoardOnDistanceToOpponentKings(board);
			double cornerProtector = 3.0 * scoreBoardOnCornerProtection(board);
			output = ratio + kingDistance + promotion + cornerProtector;
		}
		
		return output;
	}
	
}

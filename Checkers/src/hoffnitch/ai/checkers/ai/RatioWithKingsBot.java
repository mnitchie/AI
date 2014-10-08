package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class RatioWithKingsBot extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Piece Ratio With Kings";

    public RatioWithKingsBot(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
    }
    
    public RatioWithKingsBot(String name, PieceColor color, double ratioWeight,
            double kingWeight, double distanceWeight) {
        super(name, color, ratioWeight, kingWeight, distanceWeight);
    }

    @Override
    public double evaluateBoard(GameState board) {
        return (getRatioWeight() * scoreBoardOnPieceRatio(board))
                + (getKingWeight() * scoreBoardOnNumKings(board));
    }
}

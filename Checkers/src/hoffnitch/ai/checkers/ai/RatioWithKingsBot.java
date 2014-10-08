package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class RatioWithKingsBot extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Count With Kings";

    public RatioWithKingsBot(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
    }

    @Override
    public double evaluateBoard(GameState board) {
        return (getRatioWeight() * scoreBoardOnPieceRatio(board))
                + (getKingWeight() * scoreBoardOnNumKings(board));
    }
}

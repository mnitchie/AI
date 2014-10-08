package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class RatioWithKingsBot extends AIPlayer {

    public RatioWithKingsBot(String name, PieceColor color) {
        super(name, color);
        // TODO Auto-generated constructor stub
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

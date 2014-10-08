package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class KingBot extends AIPlayer {
    public static final String HEURISTIC_DESCRIPTION = "King Bot";

    public KingBot(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
    }

    @Override
    public double evaluateBoard(GameState board) {
        return getKingWeight() * scoreBoardOnNumKings(board);
    }
}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class PositionScorer extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Position Scorer";
    private static final double KING_MULTIPLIER = 10.1;
    
    private PositionScores[] positionScores;

    public PositionScorer(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color, 1000.0, 1.4, 2.0);
        this.positionScores = new PositionScores[32];
        for (int i = 0; i < positionScores.length; i++) {
            positionScores[i] = new PositionScores();
        }
        
        setPawnWeight(1);
        evaluatePositions();
    }
    
    private void evaluatePositions() {
        int valPerRow = 1;
        int maxRowVal = 7 * valPerRow;
        for (int i = 0; i < positionScores.length; i++) {
            int row = (i / 4);
            positionScores[i].alterBlackPawnScore(row * valPerRow);
            positionScores[i].alterRedPawnScore(maxRowVal - (row * valPerRow));
        }
    }

    @Override
    public double evaluateBoard(GameState board) {
        return 100 + scoreBoardOnPieceCount(board, KING_MULTIPLIER)
        		+ scoreBoardOnPositions(board, positionScores);
    }

}

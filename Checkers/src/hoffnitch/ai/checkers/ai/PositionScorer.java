package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;

public class PositionScorer extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Position Scorer";
    
    private PositionScores[] positionScores;

    public PositionScorer(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
        this.positionScores = new PositionScores[32];
        for (int i = 0; i < positionScores.length; i++) {
            positionScores[i] = new PositionScores();
        }
        
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
        return scoreBoardOnPositions(board);
    }

}

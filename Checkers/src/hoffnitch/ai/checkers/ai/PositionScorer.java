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
        
        for (int i = 0; i < positionScores.length; i++) {
            int row = (i / 4);
            positionScores[i].alterBlackPawnScore(row * valPerRow);
            positionScores[i].alterRedPawnScore(maxRowVal - (row * valPerRow));
        }
        
        int index = 0;
        for (int row = 0; row < 8; row++) {
        	for (int col = 0; col < 8; col++) {
        		if ((row + col) % 2 == 1) {
        			scoreForDefense(positionScores[index], row, col);
        			scoreForPromotion(positionScores[index], row);
        			scoreForCenteredness(positionScores[index], row, col);
        			
        		}
        	}
        }
        
    }
    
    /**
     * Don't leave the back row for anything.
     * Especially if you are in the top left or bottom right.
     * @param scores
     * @param row
     * @param col
     */
    private void scoreForDefense(PositionScores scores, int row, int col) {
    	if (row == 0) {
    		scores.alterBlackPawnScore(1);
    		if (col == 2) {
        		scores.alterBlackPawnScore(2);
    		}
    	}
    	else if (row == 7) {
    		scores.alterRedPawnScore(1);
    		if (col == 6) {
        		scores.alterRedPawnScore(2);
    		}
    	}
    }
    
    /**
     * Pawns should move forward
     * @param scores
     * @param row
     */
    private void scoreForPromotion(PositionScores scores, int row) {
    	final int valPerRow = 1;
        final int maxRowVal = 7 * valPerRow;
        
    	scores.alterBlackPawnScore(row * valPerRow);
    	scores.alterRedPawnScore(maxRowVal - (row * valPerRow));
    }
    
    /**
     * Go toward the center.
     * Especially if you're a king.
     * @param scores
     * @param row
     * @param col
     */
    private void scoreForCenteredness(PositionScores scores, int row, int col) {
    	// I should explain why I'm doing this.
    	if (row > 3) {
    		row = 7 - row;
    	}
    	if (col > 3) {
    		col = 7 - col;
    	}
    	
    	// I'm thinking we care more about centering kings
    	double score = Math.min(row, col);
    	double pawnScore = score / 2;
    	
    	scores.alterBlackKingScore(score);
    	scores.alterRedKingScore(score);
    	scores.alterBlackPawnScore(pawnScore);
    	scores.alterRedPawnScore(pawnScore);
    }

    @Override
    public double evaluateBoard(GameState board) {
        return 100 + scoreBoardOnPieceCount(board, KING_MULTIPLIER)
        		+ scoreBoardOnPositions(board, positionScores);
    }

}

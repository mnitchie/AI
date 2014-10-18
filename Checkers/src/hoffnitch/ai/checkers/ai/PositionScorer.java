package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

import java.util.List;

public class PositionScorer extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Position Scorer";
    private static final int DARK_PAWN = 0;
    private static final int DARK_KING = 1;
    private static final int LIGHT_PAWN = 2;
    private static final int LIGHT_KING = 3;
    
    private static final double KING_MULTIPLIER = 1.8;
    
    
    private double defenseWeight = 4;
    private double promotionWeight = 4;
    private double centeredWeight = 4;
    private double staticWeight = 80;
    
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
        int index = 0;
        for (int row = 0; row < 8; row++) {
        	for (int col = 0; col < 8; col++) {
        		if ((row + col) % 2 == 1) {
        			scoreForDefense(positionScores[index], row, col, defenseWeight);
        			scoreForPromotion(positionScores[index], row, promotionWeight);
        			scoreForCenteredness(positionScores[index], row, col, centeredWeight);
        			addStaticScore(positionScores[index], KING_MULTIPLIER, staticWeight);
        			index++;
        		}
        	}
        }
        System.out.println("--- dark pawn");
        printPositionScores(positionScores, DARK_PAWN);
        System.out.println("--- dark king");
        printPositionScores(positionScores, DARK_KING);
        System.out.println("--- light pawn");
        printPositionScores(positionScores, LIGHT_PAWN);
        System.out.println("--- light king");
        printPositionScores(positionScores, LIGHT_KING);
    }
    
    private void addStaticScore(PositionScores scores, double kingWeight, double weight) {
    	double pawnWeight = weight;
    	kingWeight *= weight;
    	scores.alterBlackKingScore(kingWeight);
    	scores.alterRedKingScore(kingWeight);
    	scores.alterBlackPawnScore(pawnWeight);
    	scores.alterRedPawnScore(pawnWeight);
    }
    
    /**
     * Don't leave the back row for anything.
     * Especially if you are in the top left or bottom right.
     * @param scores
     * @param row
     * @param col
     */
    private void scoreForDefense(PositionScores scores, int row, int col, double weight) {
    	double darkScore = 0;
    	double lightScore = 0;
    	
    	if (row == 0) {
    		darkScore += 2;
    		if (col == 1) {
    			darkScore += 3;
    		}
    	}
    	else if (row == 7) {
    		lightScore += 1;
    		if (col == 6) {
    			lightScore += 3;
    		}
    	}

    	darkScore *= weight;
    	lightScore *= weight;
    	
		scores.alterBlackPawnScore(darkScore);
		scores.alterRedPawnScore(lightScore);
    }
    
    /**
     * Pawns should move forward
     * @param scores
     * @param row
     */
    private void scoreForPromotion(PositionScores scores, int row, double weight) {
    	final int valPerRow = 1;
        final int maxRowVal = 7 * valPerRow;
        
    	scores.alterBlackPawnScore(row * valPerRow * weight);
    	scores.alterRedPawnScore((maxRowVal - (row * valPerRow)) * weight);
    }
    
    /**
     * Go toward the center.
     * Especially if you're a king.
     * @param scores
     * @param row
     * @param col
     */
    private void scoreForCenteredness(PositionScores scores, int row, int col, double weight) {
    	// I should explain why I'm doing this.
    	if (row > 3) {
    		row = 7 - row;
    	}
    	if (col > 3) {
    		col = 7 - col;
    	}
    	
    	if (col == 3) {
    		col--;
    	}
    	if (row == 3) {
    		row--;
    	}
    	
    	// I'm thinking we care more about centering kings
    	double score = Math.min(row, col) * weight;
    	double pawnScore = score / 8;
    	
    	scores.alterBlackKingScore(score);
    	scores.alterRedKingScore(score);
    	scores.alterBlackPawnScore(pawnScore);
    	scores.alterRedPawnScore(pawnScore);
    }

    @Override
    public double evaluateBoard(GameState board) {
        return scoreBoardOnPositions(board, positionScores)
        		//+ 1 * Math.random()
        		+ 1 * scoreBoardOnAlignedKings(board)
        		- 1 * totalPieceCount(board)
        		+ 0 * scoreBoardOnDistanceToPromotion(board);
    }
    
    public double totalPieceCount(GameState board) {
    	return board.getPieces(PieceColor.DARK).size() + board.getPieces(PieceColor.LIGHT).size();
    }
    
    public double scoreBoardOnAlignedKings(GameState board) {
    	double score = 0;
    	
    	List<Piece> playerPieces = board.getPieces(getColor());
    	List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
    	
    	for (Piece playerPiece: playerPieces) {
    		for (Piece opponentPiece: opponentPieces) {
    			Position playerPosition = playerPiece.getPosition();
    			Position opponentPosition = opponentPiece.getPosition();
    			
    			if (playerPiece.isCrowned() && playerPosition.row == opponentPosition.row) {
    				//score += 1;
    				if (Math.abs(playerPosition.column - opponentPosition.column) == 2) {
    					score += 1;
    				}
    			}
    			
    			else if (playerPosition.column == opponentPosition.column) {
    				//score += 1;
    				if (Math.abs(playerPosition.row - opponentPosition.row) == 2) {
    					score += 1;
    				}
    			}
    		}
    	}
    	
    	return score;
    }
    
	public static void printPositionScores(PositionScores[] scores, int type) {
		int index = 0;
		for (int row = 0; row < 8; row++) {
        	for (int col = 0; col < 8; col++) {
        		if ((row + col) % 2 == 1) {
        			PositionScores tile = scores[index];
        			double value = 0;
        			switch (type) {
        			case DARK_KING:
        				value = tile.getBlackKingScore();
        				break;
        			case DARK_PAWN:
        				value = tile.getBlackPawnScore();
        				break;
        			case LIGHT_KING:
        				value = tile.getRedKingScore();
        				break;
        			case LIGHT_PAWN:
        				value = tile.getRedPawnScore();
        				break;
        			}
        			System.out.printf(" %2.1f ", value);
        			index++;
        		} else {
        			System.out.print("     ");
        		}
        	}
        	System.out.println();
        }
	}

}

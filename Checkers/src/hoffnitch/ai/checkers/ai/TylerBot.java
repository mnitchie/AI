package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.Direction;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

import java.util.List;

public class TylerBot extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "TYLER.BOT";
    
    private static final int DARK_PAWN = 0;
    private static final int DARK_KING = 1;
    private static final int LIGHT_PAWN = 2;
    private static final int LIGHT_KING = 3;
    
    private static final double KING_MULTIPLIER = 1.8;
    
    
    private double defenseWeight = 4;
    private double promotionWeight = 4;
    private double centeredWeight = 4;
    private double kingAlignmentWeight = 0.0;
    private double pieceCountWeight = 0.0;
    private double randomWeight = 0.0;
    private double staticWeight = 80;
    
    private PositionScores[] positionScores;

    public TylerBot(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
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
    
    private void scoreForSide(PositionScores scores, int col, double pawnWeight, double kingWeight) {
    	if (col == 0 || col == 7) {
    		scores.alterBlackKingScore(kingWeight);
        	scores.alterRedKingScore(kingWeight);
        	scores.alterBlackPawnScore(pawnWeight);
        	scores.alterRedPawnScore(pawnWeight);
    	}
    }
    
    private void scoreForRing(PositionScores scores, int row, int col, int ring, double pawnWeight, double kingWeight) {
    	if (row > 3) {
    		row = 7 - row;
    	}
    	if (col > 3) {
    		col = 7 - col;
    	}
    	int positionRing = Math.min(row, col);
    	if (positionRing == ring) {
    		scores.alterBlackKingScore(kingWeight);
        	scores.alterRedKingScore(kingWeight);
        	scores.alterBlackPawnScore(pawnWeight);
        	scores.alterRedPawnScore(pawnWeight);
    	}
    }
    
    private void scoreForDefendingCorner(PositionScores scores, int row, int col, double weight) {
    	double darkScore = 0;
		double lightScore = 0;
		
		if (row == 0 && col == 1) {
			darkScore += weight;
		}
		else if (row == 7 && col == 6) {
			lightScore += weight;
		}
		
		scores.alterBlackPawnScore(darkScore);
		scores.alterRedPawnScore(lightScore);
    	
    }
    
    private void scoreForBackRow(PositionScores scores, int row, int col, double weight) {
    	double darkScore = 0;
    	double lightScore = 0;
    	
    	if (row == 0) {
    		darkScore += weight;
    	}
    	else if (row == 7) {
    		lightScore += weight;
    	}
    	
		scores.alterBlackPawnScore(darkScore);
		scores.alterRedPawnScore(lightScore);
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
        		+ randomWeight * Math.random()
        		+ kingAlignmentWeight * scoreBoardOnAlignedKings(board)
        		+ pieceCountWeight * totalPieceCount(board);
    }
    
    private double totalPieceCount(GameState board) {
    	return board.getPieces(PieceColor.DARK).size() + board.getPieces(PieceColor.LIGHT).size();
    }
    
    private double scoreBoardOnAlignedPawns(GameState board) {
    	double score = 0;
    	
    	int sideMultiplier = (getColor() == PieceColor.DARK)? 1: 0;
    	
    	List<Piece> playerPieces = board.getPieces(getColor());
    	List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
    	
    	for (Piece playerPiece: playerPieces) {
    		if (!playerPiece.isCrowned()) {
	    		for (Piece opponentPiece: opponentPieces) {
	    			Position playerPosition = playerPiece.getPosition();
	    			Position opponentPosition = opponentPiece.getPosition();
	    			
	    			if (sideMultiplier * playerPosition.row < sideMultiplier * opponentPosition.row
	    					&& playerPosition.column == opponentPosition.column
	    					&& Math.abs(playerPosition.row - opponentPosition.row) == 2) {
	    				score += 1;
	    			}
	    		}
    		}
    	}
    	return score;
    }
    
    private double scoreBoardOnAlignedKings(GameState board) {
    	double score = 0;
    	
    	List<Piece> playerPieces = board.getPieces(getColor());
    	List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
    	
    	for (Piece playerPiece: playerPieces) {
    		if (playerPiece.isCrowned()) {
	    		for (Piece opponentPiece: opponentPieces) {
	    			Position playerPosition = playerPiece.getPosition();
	    			Position opponentPosition = opponentPiece.getPosition();
	    			
	    			if (playerPosition.row == opponentPosition.row) {
	    				if (Math.abs(playerPosition.column - opponentPosition.column) == 2) {
	    					score += 1;
	    				}
	    			}
	    			
	    			else if (playerPosition.column == opponentPosition.column) {
	    				if (Math.abs(playerPosition.row - opponentPosition.row) == 2) {
	    					score += 1;
	    				}
	    			}
	    		}
    		}
    	}
    	return score;
    }
    
    private double scoreOnDiagonalTeammate(GameState board) {
    	return scoreOnDiagonalTeammate(board, getColor()) - scoreOnDiagonalTeammate(board, PieceColor.opposite(getColor())); 
    }
    
    private double scoreOnDiagonalTeammate(GameState board, PieceColor color) {
    	double score = 0;
    	List<Piece> pieces = board.getPieces(color);
    	for (int i = 0; i < pieces.size(); i++) {
    		Position positionA = pieces.get(i).getPosition();
    		for (int j = 0; j < pieces.size(); j++) {
    			Position positionB = pieces.get(j).getPosition();
    			if (Math.abs(positionA.row - positionB.row) == 1 && Math.abs(positionA.column - positionB.column) == 1) {
    				score++;
    			}
    		}
    	}
    	return score;
    }
    
    private double scoreOnTunneling(GameState board) {
    	return scoreOnTunneling(board, getColor()) - scoreOnTunneling(board, PieceColor.opposite(getColor()));
    }
    
    private double scoreOnTunneling(GameState board, PieceColor color) {
    	double score = 0;
    	List<Piece> playerPieces = board.getPieces(color);
    	
    	for (int i = 0; i < playerPieces.size(); i++) {
    		Position position = playerPieces.get(i).getPosition();
    		
    		// test first direction
    		Position topLeft = position.getOffsetPosition(Direction.TOP_LEFT);
    		Position bottomRight = position.getOffsetPosition(Direction.BOTTOM_RIGHT);
    		
    		// i wish this line were longer...
    		if ((topLeft == null || board.getPieceAtPosition(topLeft) != null && (bottomRight == null) || board.getPieceAtPosition(bottomRight) != null)) {
    			score++;
    		}
    		
    		// test other direction
    		Position topRight = position.getOffsetPosition(Direction.TOP_LEFT);
    		Position bottomLeft = position.getOffsetPosition(Direction.BOTTOM_RIGHT);
    		if ((topRight == null || board.getPieceAtPosition(topRight) != null && (bottomLeft == null) || board.getPieceAtPosition(bottomLeft) != null)) {
    			score++;
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


	public double getDefenseWeight() {
		return defenseWeight;
	}


	public void setDefenseWeight(double defenseWeight) {
		this.defenseWeight = defenseWeight;
	}


	public double getPromotionWeight() {
		return promotionWeight;
	}


	public void setPromotionWeight(double promotionWeight) {
		this.promotionWeight = promotionWeight;
	}


	public double getCenteredWeight() {
		return centeredWeight;
	}


	public void setCenteredWeight(double centeredWeight) {
		this.centeredWeight = centeredWeight;
	}


	public double getKingAlignmentWeight() {
		return kingAlignmentWeight;
	}


	public void setKingAlignmentWeight(double kingAlignmentWeight) {
		this.kingAlignmentWeight = kingAlignmentWeight;
	}


	public double getPieceCountWeight() {
		return pieceCountWeight;
	}


	public void setPieceCountWeight(double pieceCountWeight) {
		this.pieceCountWeight = pieceCountWeight;
	}


	public double getRandomWeight() {
		return randomWeight;
	}


	public void setRandomWeight(double randomWeight) {
		this.randomWeight = randomWeight;
	}


	public double getStaticWeight() {
		return staticWeight;
	}


	public void setStaticWeight(double staticWeight) {
		this.staticWeight = staticWeight;
	}


	public PositionScores[] getPositionScores() {
		return positionScores;
	}


	public void setPositionScores(PositionScores[] positionScores) {
		this.positionScores = positionScores;
	}
	

}

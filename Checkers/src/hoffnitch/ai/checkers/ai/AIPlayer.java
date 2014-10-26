package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;
import hoffnitch.ai.search.CheckersTree;

import java.util.List;

public abstract class AIPlayer extends NonHumanPlayer {
	
	private CheckersTree turnTree;
	private double ratioWeight;
	private double kingWeight;
	private double pawnWeight;
	private double distanceWeight;
	
	public AIPlayer(String name, PieceColor color) {
        super(name, color);
        ratioWeight = 1;
        kingWeight = .4;
        distanceWeight = 1;
    }
    
    public AIPlayer(String name, PieceColor color, double ratioWeight,
            double kingWeight, double distanceWeight) {
        super(name, color);
        this.ratioWeight = ratioWeight;
        this.kingWeight = kingWeight;
        this.distanceWeight = distanceWeight;
    }  
    
    public double getRatioWeight() {
        return ratioWeight;
    }

    public void setRatioWeight(double ratioWeight) {
        this.ratioWeight = ratioWeight;
    }

    public double getKingWeight() {
        return kingWeight;
    }

    public void setKingWeight(double kingWeight) {
        this.kingWeight = kingWeight;
    }
    
    public double getPawnWeight()
	{
		return pawnWeight;
	}

	public void setPawnWeight(double pawnWeight)
	{
		this.pawnWeight = pawnWeight;
	}

	public double getDistanceWeight() {
        return distanceWeight;
    }

    public void setDistanceWeight(double distanceWeight) {
        this.distanceWeight = distanceWeight;
    }
	
	public void setBoard(GameState initialBoard, PieceColor yourColor, PieceColor firstColor, int maxDepth) {
		turnTree = new CheckersTree(initialBoard, yourColor, firstColor, maxDepth);
	}
	
	public CheckersTree getTurnTree() {
		return turnTree;
	}

	public void evaluateTurns() {
		turnTree.evaluateNodes(this);
	}

	/**
	 * @param board
	 * @return Returns double representing board evaluation (higher values are better)
	 */
	public abstract double evaluateBoard(GameState board);
	
	/**
	 * Get the best turn.
	 * The assumes evaluateTurns() has already been called.
	 * @return Returns the best turn
	 */
	@Override
	public Turn getTurn() {
		evaluateTurns();
		return turnTree.getBestTurn();
	}
	
	@Override
	public void receiveOpponentTurn(Turn turn) throws InvalidTurnException {
		turnTree.doOpponentTurn(turn);
		turnTree.evaluateNodes(this);
	}
	
	/**
	 * Scores the board based on the ratio of player pieces to opponent pieces
	 * @param board The current game board
	 * @return A double representing the ratio of playerPieces:totalPieces
	 */
	protected double scoreBoardOnPieceRatio(GameState board) {
	    double numBotPieces = board.getPieces(getColor()).size();
        double numOpponentPieces = board.getPieces(PieceColor.opposite(getColor())).size();
        
        return numBotPieces / (numBotPieces + numOpponentPieces);
	}
	
	/**
	 * Scores the board based on the ratio of player pieces to opponent pieces
	 * Kings are given higher values than pawns
	 * @param board The current game board
	 * @return A double representing the ratio of playerPieces:totalPieces
	 */
	protected double scoreBoardOnWeightedRatio(GameState board, double kingWeight) {
		List<Piece> botPieces = board.getPieces(getColor());
		List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
		
		int numBotPieces = botPieces.size();
        int numOpponentPieces = opponentPieces.size();

        int numBotKings = countKings(botPieces);
        int numOpponentKings = countKings(opponentPieces);
        
        double botScore = numBotPieces + numBotKings * kingWeight;
        double opponentScore = numOpponentPieces + numOpponentKings * kingWeight;
        
        return botScore / (botScore + opponentScore);
	}
	
	protected double scoreBoardOnPieceCount(GameState board, double kingMultiplier) {
		double score = 0;
		
		List<Piece> botPieces = board.getPieces(getColor());
		List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
		
		for (Piece piece: botPieces) {
			score += (piece.isCrowned())? kingMultiplier: 1;
		}
		
		for (Piece piece: opponentPieces) {
			score -= (piece.isCrowned())? kingMultiplier: 1;
		}
		
		return score;
	}
	
	/**
     * Scores the board based on the ratio of player kings to opponent kings
     * @param board The current game board
     * @return A double representing the ratio of playerKings:totalKings
     */
	protected double scoreBoardOnNumKings(GameState board) {
	    List<Piece> botPieces = board.getPieces(getColor());
	    List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
	    
	    int numBotKings = countKings(botPieces);
	    
	    int numOpponentKings = countKings(opponentPieces);

	    int totalKings = numBotKings + numOpponentKings;
	    totalKings = totalKings == 0 ? 1 : totalKings;
	    
	    return numBotKings / (double) totalKings;
	}
	
	private int countKings(List<Piece> pieces) {
	    int toReturn = 0;
	    for (Piece p : pieces) {
	        if (p.isCrowned()) {
	            toReturn++;
	        }
	    }
	    
	    return toReturn;
	}
	
	/**
	 * Scores the board based on the aggregate mahattan distance of all player pieces
	 * to all opponent pieces. The purpose is to 'nudge' the piece nearer
	 * to the opponent to avoid a stupid stalemate of pieces moving back and forth.
	 * 
	 * Distances represented in terms of percent of board size (i.e. 1.0 indicates
	 * the pieces are on opposite corners of the board.
	 * 
	 * The returned score averaged. Thus, the returned score should be a value
	 * between 0.0 and 1.0.
	 * 
	 * @param board
	 * @return Score representing the reciprocal manhattan distance
	 */
	protected double scoreBoardOnDistanceToOpponent(GameState board) {
		// the farthest distance is 7 rows away and 7 columns away
		final int MAX_DIST = 14;
		
		List<Piece> botPieces = board.getPieces(getColor());
	    List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));
	    
	    double distanceScore = 0;
		for (Piece playerPiece: botPieces) {
			for (Piece opponentPiece: opponentPieces) {
				int rowDist = opponentPiece.getPosition().row - playerPiece.getPosition().row;
				int colDist = opponentPiece.getPosition().column - playerPiece.getPosition().column;
				distanceScore += Math.abs(rowDist) + Math.abs(colDist) / MAX_DIST;
			}
		}
		
		// average it
		distanceScore /= botPieces.size() * opponentPieces.size();
	    
		// return the reciprocal
	    return -distanceScore;
	}
	
	protected double scoreBoardOnDistanceToOpponentKings(GameState board) {
		// the farthest distance is 7 rows away and 7 columns away
		final int MAX_DIST = 14;
		
		List<Piece> botPieces = board.getPieces(getColor());
	    List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(getColor()));

	    int numBotKings = countKings(botPieces);
	    int numOpponentKings = countKings(opponentPieces);
	    
	    if (numOpponentKings == 0 || numBotKings == 0)
	    	return 0;
	    
	    double distanceScore = 0;
		for (Piece playerPiece: botPieces) {
			if (playerPiece.isCrowned()) {
				for (Piece opponentPiece: opponentPieces) {
					if (opponentPiece.isCrowned()) {
						int rowDist = opponentPiece.getPosition().row - playerPiece.getPosition().row;
						int colDist = opponentPiece.getPosition().column - playerPiece.getPosition().column;
						distanceScore += Math.abs(rowDist) + Math.abs(colDist) / MAX_DIST;
					}
				}
			}
		}
		
		// average it
		distanceScore /= numBotKings * numOpponentKings;
	    
		// return the reciprocal
	    return -distanceScore;
	}
	
	protected double scoreBoardOnDistanceToCenter(GameState board) {
		final double MAX_DISTANCE = 3.5;
		
		double distance = 0;
		List<Piece> pieces = board.getPieces(getColor());
		for (Piece piece: pieces) {
			distance += Math.abs(3.5 - piece.getPosition().row)
					+ Math.abs(3.5 - piece.getPosition().column);
		}
		distance /= (MAX_DISTANCE);
		distance /= (pieces.size());
		
		return -distance;
	}
	
	/**
	 * Returns an int representing the number of your pieces remaining in
	 * the back row, preventing the opponent from being kinged.
	 * @param board
	 * @return The number of pieces in the back row.
	 */
	protected int scoreBoardOnPromotionPrevention(GameState board) {
	    int numProtectors = 0;
	    int min = 0;
	    int max = 0;
	    switch (getColor()) {
	    case DARK:
	        min = 1;
	        max = 4;
	        break;
	    case LIGHT:
	        min = 29;
	        max = 32;
	        break;
	    }
	    
	    for (int i = min; i <= max; i++) {
            if (board.getPieceAtPosition(i).color.equals(getColor())) {
                numProtectors++;
            }
        }
	    return numProtectors;
	}
	
	/**
	 * Returns a value representing the aggregate distance between the
	 * pawns and the back row. Closer distance is desired, but the function
	 * will return a "High" number if the distance is small, and a "low" number
	 * if the distance is large.
	 * @param board
	 * @return
	 */
	protected double scoreBoardOnDistanceToPromotion(GameState board) {
		
		final double MAX_PAWNS = 12;
		final double MAX_ROWS = 7;
		
		// dark tries to get to row 7; light tries to get to row 0
		int goalRow = (getColor() == PieceColor.DARK)? 7: 0;
		double aggregateDistance = 0;
		
		List<Piece> pieces = board.getPieces(getColor());
		for (Piece piece: pieces) {
			if (!piece.isCrowned()) {
				// add to aggregate distance. Normalize to between 0 and 1
				aggregateDistance += Math.abs(goalRow - piece.getPosition().row) / MAX_ROWS;
			}
		}
		
		/*
		 * Add max distance for dead pieces so that losing pieces doesn't up this score.
		 * Does that sound reasonable?
		 */
		aggregateDistance += MAX_PAWNS - pieces.size();
		
		// get average
		aggregateDistance /= MAX_PAWNS;
		
	    // prevent division by 0
		if (aggregateDistance == 0) {
			return 1;
		} else {
			// The distance should be inversed. That is, if a piece is
		    // 10 spaces away, the value should be 1/10. 
			return 1 - aggregateDistance;
		}
	}
	
	/**
	 * Scores the board based on the open spaces on the opponent's back row
	 * @param board
	 * @return
	 */
	protected int scoreBoardOnOpenPromotionSpaces(GameState board) {
	    return 0;
	}
	
	/**
	 * Scores the board based on the number of pieces that can make a move
	 * (aren't stuck)
	 * @param board
	 * @return
	 */
	protected int scoreBoardOnNumMoveablePieces(GameState board) {
	    return 0;
	}
	
	/**
	 * Scores the board based on the number of pieces on an.
	 * @param board
	 * @return
	 */
	protected int scoreBoardOnNumSafePieces(GameState board) {
	    return 0;
	}
	
	/**
	 * 
	 * @param board
	 * @return
	 * @deprecated - Use scoreBoardOnPositions
	 */
	protected double scoreBoardOnCornerProtection(GameState board) {
		final int TOP_LEFT = 1;
		final int BOTTOM_RIGHT = 32;
		Piece piece = null;
		switch (getColor()) {
		case DARK:
			piece = board.getPieceAtPosition(TOP_LEFT);
			break;
		case LIGHT:
			piece = board.getPieceAtPosition(BOTTOM_RIGHT);
			break;
		}
		return (piece != null && !piece.isCrowned() && piece.color == getColor())? 1: 0;
	}
	
	/**
	 * Score the board based on what pieces are in what position.
	 * For this to be called, positionScores must have been set
	 * @param board GameState to evaluate
	 * @return Real number representing goodness of board
	 */
	protected double scoreBoardOnPositions(GameState board, PositionScores[] positionScores) {
		int multiplier = (getColor() == PieceColor.DARK)? 1: -1;
		double score = 0;
		for (int i = 1; i <= 32; i++) {
			Piece piece = board.getPieceAtPosition(i);
			if (piece != null) {
				PositionScores positionScore = positionScores[i - 1];
				// handle dark pieces
				if (piece.color == PieceColor.DARK) {
					if (piece.isCrowned()) {
						score += multiplier * positionScore.getBlackKingScore();
					} else {
						score += multiplier * positionScore.getBlackPawnScore();
					}
				}
				
				// handle light pieces
				else {
					if (piece.isCrowned()) {
						score -= multiplier * positionScore.getRedKingScore();
					} else {
						score -= multiplier * positionScore.getRedPawnScore();
					}
				}
			}
		}
		return score;
	}
	
}

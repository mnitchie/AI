package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;
import hoffnitch.ai.search.CheckersTree;

import java.util.List;

public abstract class AIPlayer extends Player {
	
	private CheckersTree turnTree;
	private double ratioWeight;
	private double kingWeight;
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

    public double getDistanceWeight() {
        return distanceWeight;
    }

    public void setDistanceWeight(double distanceWeight) {
        this.distanceWeight = distanceWeight;
    }
	
	public void setBoard(GameState initialBoard, PieceColor yourColor, PieceColor firstColor, int maxDepth) {
		turnTree = new CheckersTree(initialBoard, yourColor, firstColor, maxDepth);
	}
	
	public void getOpponentTurn(Turn turn) throws InvalidTurnException {
		turnTree.doOpponentTurn(turn);
		turnTree.evaluateNodes(this);
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
	public Turn getTurn() {
		return turnTree.getBestTurn();
	}
	
	/**
	 * Scores the board based on the ratio of player pieces to opponent pieces
	 * @param board The current game board
	 * @return A double representing the ratio of playerPieces:totalPieces
	 */
	protected double scoreBoardOnPieceRatio(GameState board) {
	    double numBotPieces = board.getPieces(color).size();
        double numOpponentPieces = board.getPieces(PieceColor.opposite(color)).size();
        
        return numBotPieces / (numBotPieces + numOpponentPieces);
	}
	
	/**
     * Scores the board based on the ratio of player kings to opponent kings
     * @param board The current game board
     * @return A double representing the ratio of playerKings:totalKings
     */
	protected double scoreBoardOnNumKings(GameState board) {
	    List<Piece> botPieces = board.getPieces(color);
	    List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(color));
	    
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
	 * @return Score representing manhattan distance
	 */
	protected double scoreBoardOnDistanceToOpponent(GameState board) {
		// the farthest distance is 7 rows away and 7 columns away
		final int MAX_DIST = 14;
		
		List<Piece> botPieces = board.getPieces(color);
	    List<Piece> opponentPieces = board.getPieces(PieceColor.opposite(color));
	    
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
	    
	    return distanceScore;
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
	    switch (color) {
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
            if (board.getPieceAtPosition(i).color.equals(color)) {
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
	protected int scoreBoardOnDistanceToPromotion(GameState board) {
	    // The distance should be inversed. That is, if a piece is
	    // 10 spaces away, the value should be 1/10. 
	    return 0;
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
	
	
}

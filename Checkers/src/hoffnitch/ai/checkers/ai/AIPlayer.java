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
        kingWeight = 1;
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
	
	protected double scoreBoardOnPieceRatio(GameState board) {
	    double numBotPieces = board.getPieces(color).size();
        double numOpponentPieces = board.getPieces(PieceColor.opposite(color)).size();
        
        return numBotPieces / (numBotPieces + numOpponentPieces);
	}
	
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
	
	protected double scoreBoardOnDistanceToOpponent(GameState board) {
	    return 0;
	}
}

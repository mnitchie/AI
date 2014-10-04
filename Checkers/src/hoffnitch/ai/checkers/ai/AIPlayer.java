package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;
import hoffnitch.ai.search.CheckersTree;

import java.util.List;

public abstract class AIPlayer extends Player {
	
	private CheckersTree turnTree;
	
	public AIPlayer(String name, PieceColor color) {
		super(name, color);
	}
	
	public void setBoard(GameState initialBoard, PieceColor firstColor, int maxDepth) {
		turnTree = new CheckersTree(initialBoard, firstColor, maxDepth);
	}
	
	public void getOpponentTurn(Turn turn) throws InvalidTurnException {
		turnTree.doOpponentTurn(turn);
		turnTree.evaluateNodes(this);
	}
	
	
	/**
	 * @param board
	 * @return Returns double representing board evaluation (higher values are better)
	 */
	public abstract double evaluateBoard(GameState board);
	
	/**
	 * @param options
	 * @return
	 */
	@Deprecated
	public abstract Turn getTurn(List<Turn> options);
	
}

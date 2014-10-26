package hoffnitch.ai.search;

import hoffnitch.ai.checkers.CheckersTurnMoveGenerator;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.ai.AIPlayer;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;

import java.util.ArrayList;
import java.util.List;

public class CheckersTree
{
	private SearchNode root;
	private int maxDepth;
	private final PieceColor playerColor;
	private final PieceColor opponentColor;
	
	public CheckersTree(GameState initialBoard, PieceColor playerColor, PieceColor firstColor, int maxDepth) {
		root = new SearchNode(initialBoard);
		this.maxDepth = maxDepth;
		this.playerColor = playerColor;
		this.opponentColor = PieceColor.opposite(playerColor);
		generate(root, firstColor, maxDepth);
	}
	
	private void generate(SearchNode node, PieceColor color, int depth) {
		if (node.turn != null && node.turn.pieceColor == color)
			System.out.println("SHIT");
		
		if (depth > 0) {
			PieceColor opponentColor = PieceColor.opposite(color);
			
			// is leaf; must be expanded
			if (node.isLeaf()) {
				CheckersTurnMoveGenerator turnGenerator = new CheckersTurnMoveGenerator();
				List<Turn> turns = turnGenerator.getMovesForTurn(color, node.board);
				for (Turn turn: turns) {
					
					SearchNode child = new SearchNode(turn, node.board);
					node.children.add(child);
					generate(child, opponentColor, depth - 1);
				}
			}
			
			else {
				// has children; recurse and maybe add to leaf nodes
				for (SearchNode child: node.children) {
					generate(child, opponentColor, depth - 1);
				}
			}
		}
	}
	
	public void evaluateNodes(AIPlayer ai) {
		max(root, ai);
//		System.out.println(root);
	}
	
	private void miniMax(SearchNode node, AIPlayer ai) {
		// base case
		if (node.isLeaf()) {
			node.score = ai.evaluateBoard(node.board);
		}
		
		// recurse
		else {
			// recursively evaluate children
			for (SearchNode child: node.children) {
				max(child, ai);
			}
			
			// select the max
			double min = node.children.get(0).score;
			for (int i = 1; i < node.children.size(); i++) {
				SearchNode child = node.children.get(i);
				if (child.score < min) {
					min = child.score;
				}
			}
			node.score = min;
		}
	}
	
	private void max(SearchNode node, AIPlayer ai) {
		// base case
		if (node.isLeaf()) {
			node.score = ai.evaluateBoard(node.board);
		}
		
		// recurse
		else {
			// recursively evaluate children
			for (SearchNode child: node.children) {
				miniMax(child, ai);
			}
			
			// select the max
			double max = node.children.get(0).score;
			for (int i = 1; i < node.children.size(); i++) {
				SearchNode child = node.children.get(i);
				if (child.score > max) {
					max = child.score;
				}
			}
			node.score = max;
		}
	}
	
	public Turn getBestTurn() {
		SearchNode bestNode = root.children.get(0);
		
		for (int i = 1; i < root.children.size(); i++) {
			SearchNode child = root.children.get(i);
			if (child.score > bestNode.score) {
				bestNode = child;
			}
		}
		root = bestNode;
		
		// generate turns (opponent goes next)
		generate(root, opponentColor, maxDepth);
		
		root.turn.resetIterator();
		System.out.println(playerColor);
		System.out.println(root.score);
		return root.turn;
	}
	
	public void doOpponentTurn(Turn turn) throws InvalidTurnException {
		SearchNode node = null;
		
		for (SearchNode child: root.children) {
			if (child.turn.equals(turn)) {
				node = child;
				break;
			}
		}
		
		if (node == null) {
			throw new InvalidTurnException();
		} else {
			root = node;
			
			// generate turns (you go next)
			generate(root, playerColor, maxDepth);
		}
	}
	
	public boolean turnIsAvailable(Turn turn) {
	    for (SearchNode n : root.children) {
	        if (n.turn.equals(turn)) {
	            n.score = Double.MAX_VALUE;
	            return true;
	        }
	    }
	    return false;
	}
	
	public void preventInverse(Turn turn) {
	    if (turn == null) {
	        return;
	    }
	    for (SearchNode n : root.children) {
	        if (n.turn.isInverseOf(turn)) {
	            n.score = Integer.MIN_VALUE;
	        }
	    }
	}
	
	private class SearchNode
	{
		private GameState board;
		private Turn turn;
		private double score;
		private List<SearchNode> children = new ArrayList<SearchNode>();
		
		public SearchNode(GameState board) {
			this.board = new GameState(board);
		}
		
		public SearchNode(Turn turn, GameState previousBoard) {
			this.turn = turn;
			board = new GameState(previousBoard);
			board.doTurn(turn);
		}
		
		public boolean isLeaf() {
			return children.size() == 0;
		}
		
		public String toString() {
			return "" + this.score;
		}
	}
}

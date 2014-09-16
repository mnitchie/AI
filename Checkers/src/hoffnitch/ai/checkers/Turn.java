package hoffnitch.ai.checkers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Turn {
	
	public final Piece piece;
	private LinkedList<Position> moves = new LinkedList<Position>();
	private Iterator<Position> iterator;
	private Position currentPosition;
	private Position nextPosition;
	
	public Turn(Piece piece) {
		this.piece = piece;
		moves = new LinkedList<Position>();
	}
	
	public Turn(Piece piece, Position move) {
	    this.piece = piece;
	    addMove(move);
	}
	
	public boolean containsJump() {
		if (moves.size() == 2) {
			RowAndColumn first = moves.get(0).getRowAndColumn();
			RowAndColumn second = moves.get(1).getRowAndColumn();
			
			if (Math.abs(first.row - second.row) == 1)
				return false;
			else
				return true;
			
		} else {
			return true;
		}
	}
	
	public Turn(Piece piece, List<Position> positions) {
	    this.piece = piece;
	    for (Position p : positions) {
	        moves.offer(p); // This might be backwards.
	    }
	    resetIterator();
	}

//	public Iterator<Position> iterator() {
//		return moves.iterator();
//	}
	
	public void resetIterator()
	{
		iterator = moves.iterator();
		currentPosition = iterator.next();
		nextPosition = iterator.next();
	}
	
	public Turn addMove(Position space) {
		moves.offer(space);
		if (moves.size() > 1)
			resetIterator();
		return this;
	}
	
	public Position getCurrentPosition() {
		return currentPosition;
	}
	
	public Position peekNextMove() {
		return nextPosition;
	}
	
	public boolean hasNextMove() {
		return nextPosition != null;
	}
	
	public Position nextMove() {
		currentPosition = nextPosition;
		
		if (iterator.hasNext())
			nextPosition = iterator.next();
		else
			nextPosition = null;
		
		return currentPosition;
	}
	
	public int numPositions() {
		return moves.size();
	}
	
	public String toString() {
	    StringBuilder toReturn = new StringBuilder();
	    toReturn.append(piece.color + ":");
	    boolean first = true;
	    for (Position p : moves) {
	        if (!first) {
	            toReturn.append("-");
	        }
	        toReturn.append(p.getIndex());
	        first = false;
	    }
	    return toReturn.toString();
	}
	
	// TODO: Generate an equals method to test if the attempted move is one of the 
	// available moves.
	
}

package hoffnitch.ai.checkers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Turn {
	
	public final Piece piece;
	private LinkedList<Position2> moves = new LinkedList<Position2>();
	private Iterator<Position2> iterator;
	private Position2 currentPosition;
	private Position2 nextPosition;
	
	public Turn(Piece piece) {
		this.piece = piece;
		moves = new LinkedList<Position2>();
	}
	
	public Turn(Piece piece, Position2 move) {
	    this.piece = piece;
	    addMove(move);
	}
	
	public boolean containsJump() {
		if (moves.size() == 2) {
			if (Math.abs(moves.get(0).row - moves.get(1).row) == 1)
				return false;
			else
				return true;
			
		} else {
			return true;
		}
	}
	
	public Turn(Piece piece, List<Position2> positions) {
	    this.piece = piece;
	    for (Position2 p : positions) {
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
	
	public Turn addMove(Position2 space) {
		moves.offer(space);
		if (moves.size() > 1)
			resetIterator();
		return this;
	}
	
	public Position2 getCurrentPosition() {
		return currentPosition;
	}
	
	public Position2 peekNextMove() {
		return nextPosition;
	}
	
	public boolean hasNextMove() {
		return nextPosition != null;
	}
	
	public Position2 nextMove() {
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
	    for (Position2 p : moves) {
	        if (!first) {
	            toReturn.append("-");
	        }
	        toReturn.append(p.index);
	        first = false;
	    }
	    return toReturn.toString();
	}
	
	// TODO: Generate an equals method to test if the attempted move is one of the 
	// available moves.
	
}

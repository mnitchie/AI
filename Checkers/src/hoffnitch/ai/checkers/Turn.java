package hoffnitch.ai.checkers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Turn {
	
	public final Piece piece;
	private LinkedList<Position> moves = new LinkedList<Position>();
	
	public Turn(Piece piece) {
		this.piece = piece;
		moves = new LinkedList<Position>();
	}
	
	public Turn(Piece piece, Position move) {
	    this.piece = piece;
	    addMove(move);
	}
	
	public Turn(Piece piece, List<Position> positions) {
	    this.piece = piece;
	    for (Position p : positions) {
	        moves.offer(p); // This might be backwards.
	    }
	}

	public Iterator<Position> iterator() {
		return moves.iterator();
	}
	
	public Turn addMove(Position space) {
		moves.offer(space);
		return this;
	}
	
	public Position getNextMove() {
		return moves.poll();
	}
	
	public int numPositions() {
		return moves.size();
	}
	
	public String toString() {
	    StringBuilder toReturn = new StringBuilder();
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

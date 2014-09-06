package hoffnitch.ai.checkers;

import java.util.LinkedList;

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

	public void addMove(Position space) {
		moves.offer(space);
	}
	
	public Position getNextMove() {
		return moves.poll();
	}
	
	public String toString() {
	    StringBuilder toReturn = new StringBuilder();
	    toReturn.append(piece.getPosition().getIndex());
	    for (Position p : moves) {
	        toReturn.append("-");
	        toReturn.append(p.getIndex());
	    }
	    
	    return toReturn.toString();
	}
	
}

package hoffnitch.ai.checkers;

import java.util.LinkedList;

public class Turn {
	
	public final Piece piece;
	private LinkedList<Position> moves = new LinkedList<Position>();
	
	public Turn(Piece piece) {
		this.piece = piece;
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
	
}

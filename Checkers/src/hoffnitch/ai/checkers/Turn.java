package hoffnitch.ai.checkers;

import java.util.Queue;

public class Turn {
	
	public final Piece piece;
	private Queue<Position> moves;
	
	public Turn(Piece piece) {
		this.piece = piece;
	}

	public void addMove(Position space) {
		moves.offer(space);
	}
	
	public Position getNextMove() {
		return moves.poll();
	}
	
}

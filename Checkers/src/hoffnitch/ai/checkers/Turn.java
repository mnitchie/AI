package hoffnitch.ai.checkers;

import java.util.LinkedList;
import java.util.Queue;

public class Turn {
	
	public final Piece piece;
	private Queue<Position> moves;
	
	public Turn(Piece piece) {
		this.piece = piece;
		moves = new LinkedList<Position>();
	}

	public void addMove(Position space) {
		moves.offer(space);
	}
	
	public Position getNextMove() {
		return moves.poll();
	}
	
}

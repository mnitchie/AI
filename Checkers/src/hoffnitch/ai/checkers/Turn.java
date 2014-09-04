package hoffnitch.ai.checkers;

import java.util.Queue;

public class Turn<T> {
	
	public final Piece piece;
	private Queue<T> moves;
	
	public Turn(Piece piece) {
		this.piece = piece;
	}

	public void addMove(T space) {
		moves.offer(space);
	}
	
	public T getNextMove() {
		return moves.poll();
	}
	
}

package hoffnitch.ai.gameBasics;

public class Action {

	private Piece piece;
	private MoveChain moveChain;
	
	public Action(Piece piece, MoveChain nextMove) {
		this.piece = piece;
		this.moveChain = nextMove;
	}
	
	public Action(Piece piece, Position position) {
		this(piece, new MoveChain(position));
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public MoveChain getMoveChain() {
		return moveChain;
	}

	public void setMoveChain(MoveChain moveChain) {
		this.moveChain = moveChain;
	}
	
	/**
	 * Linked list holding each move within an action.
	 * This allows each jump in multi-jump actions to be stored.
	 * @author Tyler Hoffman <tyler.c.hoffman@gmail.com>
	 */
	public static class MoveChain {
		
		private Position position;
		private Position nextMove;
		
		public MoveChain() {
			position = null;
			nextMove = null;
		}
		
		public MoveChain(Position position) {
			this.position = position;
			nextMove = null;
		}

		public Position getPosition()
		{
			return position;
		}

		public void setPosition(Position position)
		{
			this.position = position;
		}

		public Position getNextMove()
		{
			return nextMove;
		}

		public void setNextMove(Position nextMove)
		{
			this.nextMove = nextMove;
		}
		
		
	}
}

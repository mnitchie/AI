package hoffnitch.ai.gameBasics;

public class Action {

	private Piece piece;
	
	private Position nextPosition;
	
	public Action(Piece piece, Position nextPosition) {
		this.piece = piece;
		this.nextPosition = nextPosition;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Position getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Position nextPosition) {
		this.nextPosition = nextPosition;
	}
}

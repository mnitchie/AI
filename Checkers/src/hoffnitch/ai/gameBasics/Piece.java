package hoffnitch.ai.gameBasics;

public class Piece {

	public final Color color;
	
	private Position position;
	
	// TODO: Possibly make an enum
	private boolean crowned;

	public boolean isCrowned() {
		return crowned;
	}

	public void setCrowned(boolean crowned) {
		this.crowned = crowned;
	}

	public Piece(Color color, Position position) {
		this.color = color;
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}

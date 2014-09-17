package hoffnitch.ai.checkers;

public class Piece {
	
	private Position2 position;
	private boolean crowned;
	private boolean alive;
	public final PieceColor color;
	
	public Piece(PieceColor color, Position2 position) {
		this.color = color;
		this.position = position;
		this.crowned = false;
		this.alive = true;
	}
	
	public Piece(Piece otherPiece) {
	    this.position = otherPiece.position;
	    this.color = otherPiece.color;
	    this.crowned = otherPiece.crowned;
	    this.alive = otherPiece.alive;
	}
	
	public Position2 getPosition() {
		return position;
	}

	public void setPosition(Position2 position) {
		this.position = position;
	}

	public boolean isCrowned()
	{
		return crowned;
	}

	public Piece setCrowned(boolean crowned)
	{
		this.crowned = crowned;
		return this;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
}

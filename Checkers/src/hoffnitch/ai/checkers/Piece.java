package hoffnitch.ai.checkers;

public class Piece {
	
	private Position position;
	private boolean crowned;
	public final PieceColor color;
	
	public Piece(PieceColor color, Position position) {
		this.color = color;
		this.position = position;
	}
	
	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public boolean isCrowned()
	{
		return crowned;
	}

	public void setCrowned(boolean crowned)
	{
		this.crowned = crowned;
	}
	
	
	
}

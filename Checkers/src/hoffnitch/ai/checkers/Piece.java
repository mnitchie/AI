package hoffnitch.ai.checkers;

public class Piece {
	
	private Position position;
	public final Color color;
	
	public Piece(Color color, Position position) {
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
	
	
	
}

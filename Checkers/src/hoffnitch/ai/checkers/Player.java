package hoffnitch.ai.checkers;

public abstract class Player
{
	public final String name;
	private PieceColor color;
	
	public Player(String name, PieceColor color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void setColor(PieceColor color) {
		this.color = color;
	}
}

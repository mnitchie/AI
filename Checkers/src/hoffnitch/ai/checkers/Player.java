package hoffnitch.ai.checkers;

public abstract class Player
{
	public final String name;
	public final Color color;
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public abstract Turn<Short> getMove();
	
}

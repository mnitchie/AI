package hoffnitch.ai.checkers;

import java.util.List;

public abstract class Player
{
	public final String name;
	public final PieceColor color;
	
	public Player(String name, PieceColor color) {
		this.name = name;
		this.color = color;
	}
	
	public abstract Turn getTurn(List<Turn> options);
	
}

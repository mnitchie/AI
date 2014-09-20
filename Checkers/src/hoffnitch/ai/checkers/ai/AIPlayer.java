package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public abstract class AIPlayer extends Player {
	
	public AIPlayer(String name, PieceColor color) {
		super(name, color);
	}
	
	public abstract Turn getTurn(List<Turn> options);

}

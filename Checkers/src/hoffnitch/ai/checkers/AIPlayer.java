package hoffnitch.ai.checkers;

import java.util.List;

public abstract class AIPlayer extends Player {
	
	public AIPlayer(String name, PieceColor color) {
		super(name, color);
	}
	
	public abstract Turn getTurn(List<Turn> options);

}

package hoffnitch.ai.checkers.boardSetup;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Position;

public abstract class BoardInitializer
{
	public abstract void setBoard(GameState board);
	
	public void clearBoard(GameState board) {
		for (int i = 1; i < 33; i++)
			board.setPiece(null, new Position(i));
	}
}

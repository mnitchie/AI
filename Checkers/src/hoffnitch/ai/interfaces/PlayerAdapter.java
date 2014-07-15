package hoffnitch.ai.interfaces;

import hoffnitch.ai.gameBasics.Action;
import hoffnitch.ai.gameBasics.Color;
import hoffnitch.ai.gameBasics.GameState;

public interface PlayerAdapter {
	
	Color getColor();
	
	Action promptForMove(GameState gameState);
	
	void updateGameState(Action action);
}

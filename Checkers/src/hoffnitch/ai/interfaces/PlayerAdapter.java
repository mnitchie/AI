package hoffnitch.ai.interfaces;

import hoffnitch.ai.gameBasics.Action;
import hoffnitch.ai.gameBasics.Color;

public interface PlayerAdapter {
	
	Color getColor();
	
	void promptForMove();
	
	void getOpponentMove(Action action);
}

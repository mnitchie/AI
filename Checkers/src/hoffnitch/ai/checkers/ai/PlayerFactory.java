package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.HumanPlayer;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.gui.BoardCanvas;
import hoffnitch.ai.checkers.gui.newGameMenu.PlayerInfo;

import java.util.ArrayList;

public class PlayerFactory {

	public static final String HUMAN 		= "Human Player";
	public static final String RANDOM_BOT 	= "RandomBot";
	public static final String EAGER_BOT 	= "EagerBot";
	
	private ArrayList<String> playerTypes;
	
	public PlayerFactory() {
		playerTypes = new ArrayList<String>();

		playerTypes.add(HUMAN);
		playerTypes.add(RANDOM_BOT);
		playerTypes.add(EAGER_BOT);
	}
	
	public Player getPlayer(PlayerInfo playerInfo, BoardCanvas view) {
		switch(playerInfo.getPlayerType()) {
		case HUMAN:
			return new HumanPlayer(playerInfo.getPlayerName(), playerInfo.getColor(), view);
		case RANDOM_BOT:
			return new RandomBot(playerInfo.getColor());
		case EAGER_BOT:
			return new EagerBot(playerInfo.getColor());
		}
		return null;
	}
	
	public String[] getTypes() {
		String names[] = new String[playerTypes.size()];
		return playerTypes.toArray(names);
	}
}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.HumanPlayer;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.gui.BoardCanvas;
import hoffnitch.ai.checkers.gui.newGameMenu.PlayerInfo;

import java.util.ArrayList;

public class PlayerFactory {

	public static final String HUMAN 			= "Human Player";
	public static final String REMOTE_PLAYER	= "Remote Player";
	
	private ArrayList<String> playerTypes;
	
	public PlayerFactory() {
		playerTypes = new ArrayList<String>();

		playerTypes.add(HUMAN);
		playerTypes.add(RandomBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(RatioBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(RatioWithKingsBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(KingBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(CornerDefender.HEURISTIC_DESCRIPTION);
		playerTypes.add(PositionScorer.HEURISTIC_DESCRIPTION);
	}
	
	public Player getPlayer(PlayerInfo playerInfo, BoardCanvas view) {
		switch(playerInfo.getPlayerType()) {
		case HUMAN:
			return new HumanPlayer(playerInfo.getPlayerName(), playerInfo.getColor(), view);
		case RandomBot.HEURISTIC_DESCRIPTION:
			return new RandomBot(playerInfo.getColor());
		case RatioBot.HEURISTIC_DESCRIPTION:
			return new RatioBot(playerInfo.getColor());
		case RatioWithKingsBot.HEURISTIC_DESCRIPTION:
		    return new RatioWithKingsBot(playerInfo.getColor());
		case KingBot.HEURISTIC_DESCRIPTION:
		    return new KingBot(playerInfo.getColor());
		case CornerDefender.HEURISTIC_DESCRIPTION:
		    return new CornerDefender(playerInfo.getColor());
		case PositionScorer.HEURISTIC_DESCRIPTION:
		    return new PositionScorer(playerInfo.getColor());
		}
		return null;
	}
	
	public String[] getTypes() {
		String names[] = new String[playerTypes.size()];
		return playerTypes.toArray(names);
	}
}

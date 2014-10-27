package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.HumanPlayer;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.gui.BoardCanvas;
import hoffnitch.ai.checkers.gui.newGameMenu.PlayerInfo;
import hoffnitch.ai.statistics.WeightSet;

import java.util.ArrayList;

public class PlayerFactory {

	public static final String HUMAN 			= "Human Player";
	public static final String REMOTE_PLAYER	= "Remote Player";
	
	private ArrayList<String> playerTypes;
	
	public PlayerFactory() {
		playerTypes = new ArrayList<String>();

		playerTypes.add(HUMAN);
		playerTypes.add(TylerBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(RandomBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(RatioBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(RatioWithKingsBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(KingBot.HEURISTIC_DESCRIPTION);
		playerTypes.add(CornerDefender.HEURISTIC_DESCRIPTION);
		playerTypes.add(PositionScorer.HEURISTIC_DESCRIPTION);
		playerTypes.add(CountBot.HEURISTIC_DESCRIPTION + " Control");
		playerTypes.add(CountBot.HEURISTIC_DESCRIPTION + " Experiment");
		playerTypes.add(MikeBot.HEURISTIC_DESCRIPTION);
	}
	
	public Player getPlayer(PlayerInfo playerInfo, BoardCanvas view) {
		switch(playerInfo.getPlayerType()) {
		case HUMAN:
			return new HumanPlayer(playerInfo.getPlayerName(), playerInfo.getColor(), view);
		case TylerBot.HEURISTIC_DESCRIPTION:
			return new TylerBot(playerInfo.getColor(), new WeightSet("13.68214483832713 -3.3593541227800894 -18.614011207101726 -8.427563087177857 24.733275938514286 4.2835843349729394 8.129723189583823 108.17919399457409 122.3562494551536 2.195074394060017 15.197925274939884 4.0071615713202 -18.876614405374248 21.994942261458263 29.966602152658815 -3.549389405982067 -11.00700260279995 -0.8853216694784707 -11.901819467677072"));
		case RandomBot.HEURISTIC_DESCRIPTION:
			return new RandomBot(playerInfo.getColor());
		case RatioBot.HEURISTIC_DESCRIPTION:
			return new RatioBot(playerInfo.getColor(), 1.4);
		case RatioWithKingsBot.HEURISTIC_DESCRIPTION:
		    return new RatioWithKingsBot(playerInfo.getColor());
		case KingBot.HEURISTIC_DESCRIPTION:
		    return new KingBot(playerInfo.getColor());
		case CornerDefender.HEURISTIC_DESCRIPTION:
		    return new CornerDefender(playerInfo.getColor(), 1.4);
		case PositionScorer.HEURISTIC_DESCRIPTION:
		    return new PositionScorer(playerInfo.getColor());
		case CountBot.HEURISTIC_DESCRIPTION + " Control":
		    return new CountBot(playerInfo.getColor(), 1.4);
		case CountBot.HEURISTIC_DESCRIPTION + " Experiment":
		    return new CountBot(playerInfo.getColor(), 4);
		case MikeBot.HEURISTIC_DESCRIPTION: 
            return new MikeBot(playerInfo.getColor(), 1.4);
		}
		return null;
	}
	
	public String[] getTypes() {
		String names[] = new String[playerTypes.size()];
		return playerTypes.toArray(names);
	}
}

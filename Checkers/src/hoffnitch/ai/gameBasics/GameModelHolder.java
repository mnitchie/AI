package hoffnitch.ai.gameBasics;

/**
 * Abstract class to hold its own models of gameState and players
 * @author Tyler Hoffman <tyler.c.hoffman@gmail.com>
 */
public abstract class GameModelHolder
{
	public final GameState gameState;
	public final PlayerModel playerA;
	public final PlayerModel playerB;
	
	public GameModelHolder() {
		playerA = new PlayerModel();
		playerB = new PlayerModel();
		gameState = new GameState();
	}
}

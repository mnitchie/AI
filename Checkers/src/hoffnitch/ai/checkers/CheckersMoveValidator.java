package hoffnitch.ai.checkers;

public class CheckersMoveValidator
{
	public static Turn validateTurn(Player player, Turn turn) {
		
		if (!player.color.equals(turn.piece.color)) {
			// return a message - "Wrong color"
		}
		
		// kludge to keep eclipse quiet
		return null;
	}
}

package hoffnitch.ai.checkers;

public class CheckersMoveValidator
{
	public static Turn<Short> validateTurn(Player player, Turn<Short> turn) {
		
		if (!player.color.equals(turn.piece.color)) {
			// return a message - "Wrong color"
		}
		
		// kludge to keep eclipse quiet
		return null;
	}
}

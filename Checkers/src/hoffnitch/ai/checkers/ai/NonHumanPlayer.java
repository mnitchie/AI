package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;

public abstract class NonHumanPlayer extends Player
{
	public NonHumanPlayer(String name, PieceColor color) {
		super(name, color);
	}
	
	public abstract void receiveOpponentTurn(Turn turn) throws InvalidTurnException;
	
	public abstract Turn getTurn();
}

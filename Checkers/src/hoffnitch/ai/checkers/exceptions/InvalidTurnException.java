package hoffnitch.ai.checkers.exceptions;

public class InvalidTurnException extends Exception
{
	private static final long serialVersionUID = 389039133595501383L;
	
	public static final String DESCRIPTION = "Invalid Turn";
	
	public InvalidTurnException() {
		super(DESCRIPTION);
	}
}

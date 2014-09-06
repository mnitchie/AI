package hoffnitch.ai.checkers;

public interface View
{
	public Turn getTurn();
	public void updateBoard(Turn turn);
}

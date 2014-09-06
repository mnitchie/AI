package hoffnitch.ai.checkers;


public class Demo
{
	public static void main(String[] args) {
		
		// human player
		Player red = new HumanPlayer("Mike", PieceColor.WHITE);
		
		// AI Player
		Player black = new HumanPlayer("Tyler", PieceColor.BLACK);
		
		GameState board = new GameState();
		
		while(!isOver(board)) {
			black.getTurn();
			// update view
			if (isOver(board))
				break;
			red.getTurn();
			// update view
			// 
		}
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

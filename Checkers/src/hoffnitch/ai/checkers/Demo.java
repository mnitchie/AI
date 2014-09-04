package hoffnitch.ai.checkers;

public class Demo
{
	public static void main(String[] args) {
		
		// human player
		Player red = new Player();
		
		// AI Player
		Player black = new Player();
		
		GameState board = new GameState();
		
		while(!isOver(board)) {
			black.getMove();
			// update view
			if (isOver(board))
				break;
			red.getMove();
			// update view
			// 
		}
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

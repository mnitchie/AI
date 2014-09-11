package hoffnitch.ai.checkers;

import java.util.List;


public class Demo
{
	public static void main(String[] args) {
		
		// human player
		Player white = new HumanPlayer("Mike", PieceColor.WHITE);
		
		// AI Player
		Player black = new HumanPlayer("Tyler", PieceColor.BLACK);
		
		GameState board = new GameState(CheckersScenario.TWO_PIECES_ONE_JUMP_ONE_ADJACENT);
		CheckersTurnMoveGenerator moveGenerator = new CheckersTurnMoveGenerator(board);
		
		while(!isOver(board)) {
		    List<Turn> validTurns = moveGenerator.getMovesForTurn(white.color);
			black.getTurn();
			// update view
			if (isOver(board))
				break;
			white.getTurn();
			// update view
			// 
		}
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

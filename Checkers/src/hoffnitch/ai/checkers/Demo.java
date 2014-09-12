package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.ai.RandomBot;
import hoffnitch.ai.checkers.gui.CanvasView;

import java.util.List;


public class Demo
{
	public static void main(String[] args) {
		
		GameState board = new GameState();
		CheckersTurnMoveGenerator moveGenerator = new CheckersTurnMoveGenerator(board);
		CanvasView view = new CanvasView("Checkers", board);
		
		// human player
		Player white = new HumanPlayer("Mike", PieceColor.WHITE, view.canvas);
		
		// AI Player
		Player black = new RandomBot("Brad", PieceColor.BLACK);
		
		view.setVisible(true);
		
		while(!isOver(board)) {
		    List<Turn> validTurns = moveGenerator.getMovesForTurn(black.color);
			Turn turn = black.getTurn(validTurns);
			board.doTurn(turn);
			view.canvas.syncWithGameState();
			
			if (isOver(board))
				break;
			
			validTurns = moveGenerator.getMovesForTurn(white.color);
			turn = white.getTurn(validTurns);
			board.doTurn(turn);
			view.canvas.syncWithGameState();
		}
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

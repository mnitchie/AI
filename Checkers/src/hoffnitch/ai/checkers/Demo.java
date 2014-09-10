package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.boardSetup.CheckersScenario;
import hoffnitch.ai.checkers.gui.CanvasView;

import java.util.List;


public class Demo
{
	public static void main(String[] args) {
		
		GameState board = new GameState(CheckersScenario.DOUBLE_JUMP_TWO_OPTIONS_AMBIGUOUS_TWO_OPTIONS);
		CanvasView view = new CanvasView("test", board);
		
		// human player
		Player white = new HumanPlayer("Mike", PieceColor.WHITE, view.canvas);
		
		// AI Player
		Player black = new HumanPlayer("Tyler", PieceColor.BLACK, view.canvas);
		
		CheckersMoveGenerator moveGenerator = new CheckersMoveGenerator(board);
		
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

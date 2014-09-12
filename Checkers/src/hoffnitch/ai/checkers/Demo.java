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
		Player white = new HumanPlayer("Mike", PieceColor.LIGHT, view.canvas);
		
		// AI Player
		Player black = new RandomBot("Brad", PieceColor.DARK);
		
		view.setVisible(true);
		
		while(!isOver(board)) {
		    List<Turn> validTurns = moveGenerator.getMovesForTurn(black.color);
		    System.out.println(board);
			Turn turn = black.getTurn(validTurns);
			System.out.println(turn);
			view.textArea.append(turn.toString() + "\n");
			board.doTurn(turn);
			view.canvas.syncWithGameState();
			
			if (isOver(board))
				break;
			
			validTurns = moveGenerator.getMovesForTurn(white.color);
			System.out.println(board);
			turn = white.getTurn(validTurns);
			System.out.println(turn);
			view.textArea.append(turn.toString() + "\n");
			board.doTurn(turn);
			view.canvas.syncWithGameState();
		}
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

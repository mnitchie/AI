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
		Player winner = null;
		
		while (winner == null) {
			
		    List<Turn> validTurns = moveGenerator.getMovesForTurn(black.color);
		    
		    if (validTurns.size() == 0) {
		    	winner = white;
		    	break;
		    }
		    
		    System.out.println(board);
			Turn turn = black.getTurn(validTurns);
			System.out.println(turn);
			view.textArea.append(turn.toString() + "\n");
			board.doTurn(turn);
			view.canvas.syncWithGameState();
			
			if (isOver(board)) {
				winner = black;
				break;
			}
			
			validTurns = moveGenerator.getMovesForTurn(white.color);
			if (validTurns.size() == 0) {
				winner = black;
		    	break;
			}
		    
			System.out.println(board);
			turn = white.getTurn(validTurns);
			System.out.println(turn);
			view.textArea.append(turn.toString() + "\n");
			board.doTurn(turn);
			view.canvas.syncWithGameState();
			
			if (isOver(board)) {
				winner = black;
				break;
			}
		}
		
		System.out.println(winner.color.toString() + " wins");
	}
	
	private static boolean isOver(GameState board) {
		return false;
	}
}

package hoffnitch.ai.checkers.demos;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PositionInitializer;
import hoffnitch.ai.checkers.gui.CanvasView;

public class CanvasDemo
{
	public static void main(String[] args) {
		
		GameState board = new GameState();
		PositionInitializer.initializeBoard(board);
		
		CanvasView view = new CanvasView("test", board);
		view.setVisible(true);
		
	}
}

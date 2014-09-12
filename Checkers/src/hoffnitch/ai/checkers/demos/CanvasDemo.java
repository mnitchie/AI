package hoffnitch.ai.checkers.demos;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.boardSetup.BoardInitializerFromFile;
import hoffnitch.ai.checkers.boardSetup.CheckersScenario;
import hoffnitch.ai.checkers.boardSetup.ScenarioInitializer;
import hoffnitch.ai.checkers.gui.CanvasView;

import java.io.IOException;

public class CanvasDemo
{
	public static void main(String[] args) {
		
		GameState board = new GameState();
		//new DefaultInitializer().setBoard(board);
		BoardInitializerFromFile initializer = new BoardInitializerFromFile();
		try
		{
			initializer.loadFile("data/test.txt");
			initializer.setBoard(board);
			
			// test saving file
			Position pos = new Position(3);
			Piece p = new Piece(PieceColor.DARK, pos);
			board.setPiece(p, pos);
			
			initializer.getBoard(board);
			initializer.saveFile("data/test2.txt");
			
			//new ScenarioInitializer(CheckersScenario.DOUBLE_JUMP_TWO_OPTIONS_AMBIGUOUS_TWO_OPTIONS).setBoard(board);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CanvasView view = new CanvasView("test", board);
		view.setVisible(true);
	}
}

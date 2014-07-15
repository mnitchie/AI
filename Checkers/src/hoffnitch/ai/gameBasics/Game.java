package hoffnitch.ai.gameBasics;

import hoffnitch.ai.io.ConsoleView;

public class Game
{
	public static void main(String[] args) {
		GameState board = new GameState();
		
		Player p1 = new Player();
		p1.playerA.setColor(Color.BLACK);
		Player p2 = new Player();
		p2.playerA.setColor(Color.RED);
		Action play;
		
		initializeGameState(board);
		
		ConsoleView.renderGameState(board);
		while(true) {
			play = p1.promptForMove(board);
			//board.setPiece(play.getPiece(), play.getNextPosition());
			board.updateGameState(play);
			ConsoleView.renderGameState(board);
			
			play = p2.promptForMove(board);
			//board.setPiece(play.getPiece(), play.getNextPosition());
			board.updateGameState(play);
			
			ConsoleView.renderGameState(board);
		}
	}
	
	private static void initializeGameState(GameState board) {
		initializePlayer(Color.BLACK, 0, board);
		initializePlayer(Color.RED, 5, board);
	}
	
	private static void initializePlayer(Color color, int firstRow, GameState board) {
		int secondRow = firstRow + 2;
		for (int row = firstRow; row <= secondRow; row++) {
			int firstCol = (row + 1) % 2;
			for (int col = firstCol; col < GameState.WIDTH; col += 2) {
				Position position = new Position(row, col);
				Piece piece = new Piece(color, position);
				board.setPiece(piece, position);
			}
		}
	}
	
}

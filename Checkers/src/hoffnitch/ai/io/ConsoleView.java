package hoffnitch.ai.io;

import org.ietf.jgss.GSSContext;

import hoffnitch.ai.gameBasics.Color;
import hoffnitch.ai.gameBasics.GameState;
import hoffnitch.ai.gameBasics.Piece;

public class ConsoleView {
	/**
	 * Render the current game state to the console.
	 * @param gameState Gamestate to render
	 */
	public static void renderGameState(GameState gameState) {
		Piece[][] pieces = gameState.getState();
		final String topLabels = "      A   B   C   D   E   F   G   H";
		final String horizontal = "   +---+---+---+---+---+---+---+---+";
		
		System.out.println(topLabels);
		for (int i = 0; i < GameState.WIDTH; i++) {
			System.out.println(horizontal);
			System.out.print(" " + i + " ");
			for (int j = 0; j < GameState.WIDTH; j++) {
				Piece piece = pieces[i][j];
				// get character
				char pieceIndicator = ' ';
				if (piece != null)
					if (piece.color == Color.BLACK) {
						if (piece.isCrowned())
							pieceIndicator = 'B';
						else
							pieceIndicator = 'b';
					} else {
						if (piece.isCrowned())
							pieceIndicator = 'R';
						else
							pieceIndicator = 'r';
					}
				System.out.print("| " + pieceIndicator + " ");
			}	
			System.out.println("|");
		}
		System.out.println(horizontal);
	}
}

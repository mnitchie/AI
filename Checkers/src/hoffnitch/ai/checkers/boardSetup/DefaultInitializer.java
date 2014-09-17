package hoffnitch.ai.checkers.boardSetup;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

public class DefaultInitializer extends BoardInitializer
{
	/**
	 * Assign pieces in their initial positions to the board
	 * @param board Board to assign pieces to
	 */
	public void setBoard(GameState board) {

		clearBoard(board);
		
		// set black pieces
		for (int positionIndex = 1; positionIndex < 13; positionIndex++) {
			Position position = Position.getPosition(positionIndex);
			Piece piece = new Piece(PieceColor.DARK, position);
			board.setPiece(piece, position);
		}
		
		// set red pieces
		for (int positionIndex = 21; positionIndex < 33; positionIndex++) {
			Position position = Position.getPosition(positionIndex);
			Piece piece = new Piece(PieceColor.LIGHT, position);
			board.setPiece(piece, position);
		}
	}
}

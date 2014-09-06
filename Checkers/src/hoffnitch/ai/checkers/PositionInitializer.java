package hoffnitch.ai.checkers;

public class PositionInitializer
{
	/**
	 * Assign pieces in their initial positions to the board
	 * @param board Board to assign pieces to
	 */
	public static void initializeBoard(GameState board) {

		// set black pieces
		for (int positionIndex = 1; positionIndex < 13; positionIndex++) {
			Position position = new Position(positionIndex);
			Piece piece = new Piece(PieceColor.BLACK, position);
			board.setPiece(piece, position);
		}
		
		// set red pieces
		for (int positionIndex = 21; positionIndex < 33; positionIndex++) {
			Position position = new Position(positionIndex);
			Piece piece = new Piece(PieceColor.WHITE, position);
			board.setPiece(piece, position);
		}
	}
}

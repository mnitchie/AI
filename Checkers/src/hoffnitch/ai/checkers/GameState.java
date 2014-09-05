package hoffnitch.ai.checkers;


public class GameState {
	
	public static final int WIDTH = 8;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
		
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < WIDTH; j++)
				boardPieces[i][j] = null;
	}
	
	/**
	 * TODO: Throw exception
	 * @param position
	 * @return
	 */
	public Piece getPiece(Position position) {
		if (inBounds(position))
			return boardPieces[position.row][position.col];
		else
			return null;
	}
	
	public void setPiece(Piece piece, Position position) {
		
		// TODO add error checking
		boardPieces[position.row][position.col] = piece;
		
		if (piece != null)
			piece.setPosition(position);
	}
	
	public boolean inBounds(Position position) {
		return position.row >= 0
				&& position.row < WIDTH
				&& position.col >= 0
				&& position.col < WIDTH;
	}
	
	public Piece[][] getBoard() {
	    Piece[][] toReturn = new Piece[WIDTH][WIDTH];
	    for(int i = 0; i < boardPieces.length; i++) {
	        for(int j = 0; j < boardPieces[i].length; j++) {
	            toReturn[i][j] = boardPieces[i][j];
	        }
	    }
	    return toReturn;
	}
	
	public Piece[][] cloneState() {
		Piece[][] clonedState = new Piece[WIDTH][WIDTH];
		
		for (short i = 0; i < WIDTH; i++)
			for (short j = 0; j < WIDTH; j++)
				clonedState[i][j] = boardPieces[i][j];
		
		return clonedState;
	}
	
}

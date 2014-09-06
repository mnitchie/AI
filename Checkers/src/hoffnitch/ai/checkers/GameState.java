package hoffnitch.ai.checkers;


public class GameState {
	
	public static final int WIDTH = 8;
	public static final int NUM_POSITIONS = 32;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
		
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < WIDTH; j++)
				boardPieces[i][j] = null;
	}
	
	public void setPiece(Piece piece, Position position) {
		
		// TODO add error checking
		boardPieces[position.getRowAndColumn().row][position.getRowAndColumn().column] = piece;
		
		if (piece != null)
			piece.setPosition(position);
	}
	
	// I'm not convinced that checking if a position is in bounds is the GameState's job... -MN
//	public boolean inBounds(Position position) {
//	    RowAndColumn rowAndColumn = position.getRowAndColumn();
//		return rowAndColumn.row >= 0
//				&& rowAndColumn.row < WIDTH
//				&& rowAndColumn.column >= 0
//				&& rowAndColumn.column < WIDTH;
//	}
	
	public Piece getPieceAtPosition(Position p) {
        return getPieceAtPosition(p.getRowAndColumn());
	}
	
    public Piece getPieceAtPosition(int index) {
        return getPieceAtPosition(CheckerBoardLocationLookup.getLocationFor(index));   
    }
	
	public Piece getPieceAtPosition(RowAndColumn rowAndColumn) {
        return getPieceAtPosition(rowAndColumn.row, rowAndColumn.column);
	    
	}
	
	public Piece getPieceAtPosition(int row, int column) {
        return boardPieces[row][column];
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

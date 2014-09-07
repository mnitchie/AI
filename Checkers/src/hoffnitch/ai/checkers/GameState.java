package hoffnitch.ai.checkers;


public class GameState {
	
	public static final int WIDTH = 8;
	public static final int NUM_POSITIONS = 32;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
		
		for (int i = 1; i <= NUM_POSITIONS; i++) {
		    Position position = new Position(i);
		    RowAndColumn rc = position.getRowAndColumn();
		    
		    if (i <= 12) {
		        boardPieces[rc.row][rc.column] = new Piece(PieceColor.BLACK, position);
		    } else if (i >= 21) {
		        boardPieces[rc.row][rc.column] = new Piece(PieceColor.WHITE, position);
		    } else {
		        boardPieces[rc.row][rc.column] = null;
		    }
		}
	}
	
	public GameState(CheckersScenario scenario) {
	    for (Piece p : scenario.getScenario()) {
	        setPiece(p, p.getPosition());
	    }
	}
	
	public GameState(GameState toCopy) {
	    this.boardPieces = new Piece[WIDTH][WIDTH];
	    for (int i = 0; i < WIDTH; i++) {
	        for (int j = 0; j < WIDTH; j++) {
	            this.boardPieces[i][j] = new Piece(toCopy.getPieceAtPosition(i, j)); 
	        }
	    }
	}
	
	public void setPiece(Piece piece, Position position) {
		
		// TODO add error checking
	    // Or not... Perhaps we assume that any input to this class is valid?
		boardPieces[position.getRowAndColumn().row][position.getRowAndColumn().column] = piece;
		
		if (piece != null)
			piece.setPosition(position);
	}
	
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

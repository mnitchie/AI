package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.boardSetup.CheckersScenario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameState {
	
	public static final int WIDTH = 8;
	public static final int NUM_POSITIONS = 32;
	public static final int BLACK_START_INDEX = 12;
	public static final int WHITE_START_INDEX = 21;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
		
		for (int i = 1; i <= NUM_POSITIONS; i++) {
		    Position position = Position.getPosition(i);
		    
		    if (i <= BLACK_START_INDEX) {
		        boardPieces[position.row][position.column] = new Piece(PieceColor.DARK, position);
		    } else if (i >= WHITE_START_INDEX) {
		        boardPieces[position.row][position.column] = new Piece(PieceColor.LIGHT, position);
		    } else {
		        boardPieces[position.row][position.column] = null;
		    }
		}
	}
	
	public GameState(CheckersScenario scenario) {
	    boardPieces = new Piece[WIDTH][WIDTH];
	    for (int i = 1; i <= NUM_POSITIONS; i++) {
	        setPiece(null, Position.getPosition(i));
	    }
	    for (Piece p : scenario.getScenario()) {
	        setPiece(p, p.getPosition());
	    }
	}
	
	public GameState(GameState toCopy) {
	    this.boardPieces = new Piece[WIDTH][WIDTH];
	    for (int i = 0; i < WIDTH; i++) {
	        for (int j = 0; j < WIDTH; j++) {
	            Piece pieceToCopy = toCopy.getPieceAtPosition(i, j);
	            this.boardPieces[i][j] = pieceToCopy == null ? null : new Piece(toCopy.getPieceAtPosition(i, j)); 
	        }
	    }
	}
	
	public void doTurn(Turn turn) {
		//Iterator<Position> positions = turn.iterator();
		turn.resetIterator();
		Position current = turn.getCurrentPosition();
		boolean isAdjacentMove = !turn.containsJump();
		
		Piece piece = getPieceAtPosition(turn.piecePositionIndex);
		
		// non-jump
		if (isAdjacentMove) {
			setPiece(null, current);
			setPiece(piece, turn.nextMove());
		}
		
		// jump
		else {
			
			while (turn.hasNextMove()) {
				setPiece(null, current);
				Position next = turn.nextMove();
				
				int jumpedRow = (current.row + next.row) / 2;
				int jumpedColumn = (current.column + next.column) / 2;
				
				Position jumped = Position.getPosition(jumpedRow, jumpedColumn);
				Piece removedPiece = getPieceAtPosition(jumped);
				
				removedPiece.setAlive(false);
				setPiece(null, jumped);
				
				setPiece(piece, next);
				
				current = next;
			}
		}
		
		if (shouldCrownPiece(piece)) {
		    piece.setCrowned(true);
		}
	}
	
	private boolean shouldCrownPiece(Piece piece) {
	    if (piece.isCrowned()) {
	        return false;
	    }
	    
	    if (piece.color == PieceColor.LIGHT &&
            piece.getPosition().index >= 0 &&
            piece.getPosition().index <= 4) {
                return true;
	    }
	    
	    if (piece.color == PieceColor.DARK &&
	        piece.getPosition().index >= 29 &&
	        piece.getPosition().index <= 32) {
	        return true;
	    }
	    
	    return false;
	}
	
	public void setPiece(Piece piece, Position position) {
		
		// TODO add error checking
	    // Or not... Perhaps we assume that any input to this class is valid?
		boardPieces[position.row][position.column] = piece;
		
		if (piece != null)
			piece.setPosition(position);
	}
	
	public List<Piece> getPieces(PieceColor color) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		for (int i = 1; i <= NUM_POSITIONS; i++) {
			Piece piece = getPieceAtPosition(i);
			if (piece != null && piece.color == color)
				pieces.add(piece);
		}
		
		return pieces;
	}
	
	public Piece getPieceAtPosition(Position p) {
		if (p != null)
			return boardPieces[p.row][p.column];
		else return null;
	}
	
    public Piece getPieceAtPosition(int index) {
    	return getPieceAtPosition(Position.getPosition(index));   
    }
	
	public Piece getPieceAtPosition(int row, int column) {
		return getPieceAtPosition(Position.getPosition(row, column));
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
	
	public void clear() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				boardPieces[i][j] = null;
			}
		}
	}
	
	/**
	 * Get the inverse of a GameState, in which piece positions and colors are reversed.
	 * For example, if in the original state, light has 4 pieces in its back row,
	 * and dark has 1 king on the left edge, the inverse will have 4 dark pieces on dark's
	 * back row, and 1 light king on the right edge.
	 * @return inverse
	 */
	public GameState getInverse() {
		GameState inverse = new GameState();
		inverse.clear();
		
		for (int row = 0; row < WIDTH; row++) {
			for (int col = 0; col < WIDTH; col++) {
				Piece originalPiece = boardPieces[row][col];
				if (originalPiece != null) {
					Position originalPosition = Position.getPosition(row, col);
					Position inversePosition = Position.getPosition(33 - originalPosition.index);
					Piece inversePiece = new Piece(PieceColor.opposite(originalPiece.color), inversePosition);
					
					inversePiece.setCrowned(originalPiece.isCrowned());
					
					inverse.setPiece(inversePiece, inversePosition);
				}
			}
		}
		
		return inverse;
	}
	
	public String toString() {
	    
	    StringBuilder toReturn = new StringBuilder();
        final String horizontal = "   +---+---+---+---+---+---+---+---+\n";

	    toReturn.append("     A   B   C   D   E   F   G   H\n");
	        
        for (int i = 0; i < GameState.WIDTH; i++) {
            toReturn.append(horizontal);
            toReturn.append(" " + i + " ");
            for (int j = 0; j < GameState.WIDTH; j++) {
                Piece piece = boardPieces[i][j];
                char pieceIndicator = ' ';
                if (piece != null) {
                    if (piece.color == PieceColor.DARK) {
                        if (piece.isCrowned())
                            pieceIndicator = 'B';
                        else
                            pieceIndicator = 'b';
                    } else {
                        if (piece.isCrowned())
                            pieceIndicator = 'W';
                        else
                            pieceIndicator = 'w';
                    }
                }
                toReturn.append("| " + pieceIndicator + " ");
	        }
            toReturn.append("|");
            toReturn.append("\n");
	    }
        toReturn.append(horizontal);
        return toReturn.toString();
	}
	
}

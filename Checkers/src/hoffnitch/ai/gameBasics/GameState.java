package hoffnitch.ai.gameBasics;


public class GameState {

	public static final int WIDTH = 8;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
	}
	
	public Piece getPiece(Position position) {
		return boardPieces[position.getX()][position.getY()];
	}
	
	public void setPiece(Piece piece, Position position) {
		boardPieces[position.getX()][position.getY()] = piece;
	}
	
	/**
	 * TODO: implement GameState.getState()
	 * @return current game state
	 */
	public Piece[][] getState() {
		return null;
	}
}

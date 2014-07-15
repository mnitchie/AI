package hoffnitch.ai.gameBasics;


public class GameState {

	public static final int WIDTH = 8;
	
	private Piece[][] boardPieces;
	
	public GameState() {
		boardPieces = new Piece[WIDTH][WIDTH];
		
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < WIDTH; j++)
				boardPieces[i][j] = null;
	}
	
	public Piece getPiece(Position position) {
		return boardPieces[position.getRow()][position.getCol()];
	}
	
	public void setPiece(Piece piece, Position position) {
		boardPieces[position.getRow()][position.getCol()] = piece;
	}
	
	/**
	 * TODO: implement GameState.getState()
	 * @return current game state
	 */
	public Piece[][] getState() {
		return boardPieces;
	}
	
	public void updateGameState(Action action)
	{		
		setPiece(null, action.getPiece().getPosition());
		setPiece(action.getPiece(), action.getMoveChain().getPosition());	
	}
}

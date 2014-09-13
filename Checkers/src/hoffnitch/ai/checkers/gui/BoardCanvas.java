package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.CheckerBoardLocationLookup;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.Turn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * Canvas on which the board is drawn
 */
public class BoardCanvas extends JComponent {
	private static final long serialVersionUID = 7300683785832599182L;
	
	public static final int TILE_SIZE		= 60;
	public static final Color BOARD_BLACK	= Color.GRAY;
	public static final Color BOARD_WHITE	= Color.WHITE;
	
	private GameState board;
	private GuiPiece grabbedPiece;
	private Point grabOffset;
	private List<GuiPiece> guiPieces;
	private Map<Piece, GuiPiece> pieceMap;
	private boolean canMove;

	private volatile Turn newTurn;
	private Turn turnBeingBuilt;
	private List<Iterator<Position>> turnIterators;
	private List<Turn> possibleTurns;
	
	public BoardCanvas(GameState board) {
//		guiPieces = new LinkedList<GuiPiece>();
//		pieceMap = new HashMap<Piece, GuiPiece>();
//		turnIterators = new LinkedList<Iterator<Position>>();
//		
//		this.board = board;
//		
//		//initializePieces(board);
	}
	
//	public Turn getTurn(List<Turn> possibleTurns) {
//		this.possibleTurns = possibleTurns;
//		
//		resetGetTurn();
//		canMove = true;
//		
//		// block until newTurn is set
//		while (newTurn == null);
//		
//		return newTurn;
//	}
	
//	private void resetGetTurn() {
//		newTurn = null;
//		turnBeingBuilt = null;
//		grabbedPiece = null;
//		turnBeingBuilt = null;
//	}
//	
//	
//	
//	private void filterItertors(Position position) {
//		for (int i = turnIterators.size() - 1; i >= 0; i--) {
//			Iterator<Position> iterator = turnIterators.get(i);
//			Position next = iterator.next();
//			
//			if (!(next.equals(position)))
//				turnIterators.remove(i);
//		}
//	}
	
	/**
	 * Generate a guiPieces based on pieces in gameState
	 * @param board GameState to get pieces from
	 */
//	public void initializePieces(GameState board) {
//		guiPieces.clear();
//		pieceMap.clear();
//		
//		for (short i = 1; i <= GameState.NUM_POSITIONS; i++) {
//			Piece piece = board.getPieceAtPosition(new Position(i));
//			if (piece != null) {
//				GuiPiece guiPiece = new GuiPiece(piece, TILE_SIZE);
//				guiPieces.add(guiPiece);
//				pieceMap.put(piece, guiPiece);
//			}
//		}
//		repaint();
//	}
	
//	public void syncWithGameState() {
//		for (GuiPiece piece: guiPieces)
//			piece.setCoordinates();
//		repaint();
//	}
	
	public void repaint(List<GuiPiece> guiPieces) {
		this.guiPieces = guiPieces;
		repaint();
	}
	
	/**
	 * Custom paintComponent method.
	 * Redraws the board and pieces
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		
		for (GuiPiece piece: guiPieces)
			drawPiece(g, piece);
	}

	/**
	 * Draw the tiles of the board
	 * @param g
	 */
	private void drawBoard(Graphics g) {
		int width = GameState.WIDTH;
		for (short row = 0; row < width; row++)
			for (short col = 0; col < width; col++) {
				Color color = ((row + col) % 2 == 0)? BOARD_WHITE: BOARD_BLACK;
				drawTile(g, row, col, color);
			}
	}
	
	/**
	 * Render a tile
	 * @param g Graphics object
	 * @param row Row index
	 * @param col Column index
	 * @param color Tile color
	 */
	private void drawTile(Graphics g, short row, short col, Color color) {
		g.setColor(color);
		g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	/**
	 * Render a Piece
	 * @param g Graphics object
	 * @param piece GuiPiece to render
	 */
	private void drawPiece(Graphics g, GuiPiece piece) {
		if (piece.piece.isAlive()) {
			Point coordinates = piece.getCoordinates();
			g.setColor(piece.renderColor);
			g.fillOval(coordinates.x, coordinates.y, TILE_SIZE, TILE_SIZE);
			if (piece.piece.isCrowned()) {
			    g.setColor(Color.WHITE);
			    g.drawChars("K".toCharArray(), 0, 1, coordinates.x + TILE_SIZE/2 - 5, coordinates.y + TILE_SIZE/2 + 5);
			}
		}
	}
	
	/**
	 * Get the position corresponding to pixel coordinates
	 * @param x x-coordinate (in pixels)
	 * @param y y-coordinate (in pixels)
	 * @return Position at given coordinates
	 */
	public Position getPosition(int x, int y) {
		int row = y / TILE_SIZE;
		int column = x / TILE_SIZE;
		
		Position position = null;
		
		if (CheckerBoardLocationLookup.isValidPosition(row, column))
			position = new Position(row, column);
			
		return position;
	}
	
	public Point getPositionOffset(int x, int y) {
		return new Point(x % TILE_SIZE, y % TILE_SIZE);
	}
	
	
}
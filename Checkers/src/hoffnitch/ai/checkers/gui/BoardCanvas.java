package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.CheckerBoardLocationLookup;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.RowAndColumn;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.gui.GuiPiece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * Canvas on which the board is drawn
 */
public class BoardCanvas extends JComponent implements MouseInputListener {
	private static final long serialVersionUID = 7300683785832599182L;
	
	public static final int TILE_SIZE		= 60;
	public static final Color BOARD_BLACK	= Color.GRAY;
	public static final Color BOARD_WHITE	= Color.WHITE;
	public static final Color PIECE_BLACK	= Color.BLACK;
	public static final Color PIECE_RED		= Color.RED;
	
	private GameState board;
	private GuiPiece grabbedPiece;
	private Point grabOffset;
	private List<GuiPiece> guiPieces;
	private Map<Piece, GuiPiece> pieceMap;

	private volatile Turn newTurn;
	
	public BoardCanvas(GameState board) {
		this.board = board;
		initializePieces(board);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * Generate a guiPieces based on pieces in gameState
	 * @param board GameState to get pieces from
	 */
	private void initializePieces(GameState board) {
		guiPieces = new LinkedList<GuiPiece>();
		pieceMap = new HashMap<Piece, GuiPiece>();
		
		for (short i = 1; i <= GameState.NUM_POSITIONS; i++) {
			Piece piece = board.getPiece(new Position(i));
			if (piece != null) {
				GuiPiece guiPiece = new GuiPiece(piece, TILE_SIZE);
				guiPieces.add(guiPiece);
				pieceMap.put(piece, guiPiece);
			}
		}
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
		Point coordinates = piece.getCoordinates();
		g.setColor(piece.renderColor);
		g.fillOval(coordinates.x, coordinates.y, TILE_SIZE, TILE_SIZE);
	}
	
	/**
	 * Handler for mousePress.
	 * Picks up a piece
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int row = e.getY() / TILE_SIZE;
		int column = e.getX() / TILE_SIZE;
		
		if (CheckerBoardLocationLookup.isValidPosition(row, column)) {
			Position position = new Position(row, column);
			Piece piece = board.getPiece(position);
			if (piece != null) {
				grabbedPiece = pieceMap.get(piece);
				grabbedPiece.setMoving(true);
				grabOffset = new Point(e.getX() % TILE_SIZE, e.getY() % TILE_SIZE);
				newTurn = new Turn(grabbedPiece.piece);
			}
		}
	}
	
	/**
	 * Handler for mouseRelease event.
	 * Drops the current piece
	 * @param e MouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (grabbedPiece != null) {
			int row = e.getY() / TILE_SIZE;
			int column = e.getX() / TILE_SIZE;
			
			if (CheckerBoardLocationLookup.isValidPosition(row, column)) {
				// valid position; move piece
				Position position = new Position(row, column);
				newTurn.addMove(position);
				
				// TODO: check if more jumps are available
				// TODO: update gamestate
				grabbedPiece.piece.setPosition(position);
				grabbedPiece.setCoordinates(new Point(column * TILE_SIZE, row * TILE_SIZE));
				grabbedPiece.setMoving(false);
				grabbedPiece = null;
				grabOffset = null;
			} else {
				// invalid position; move piece to previous position
				RowAndColumn originalLocation = grabbedPiece.piece.getPosition().getRowAndColumn();
				grabbedPiece.setCoordinates(new Point(originalLocation.column * TILE_SIZE, originalLocation.row * TILE_SIZE));
				grabbedPiece.setMoving(false);
				grabbedPiece = null;
				grabOffset = null;
				newTurn = null;
			}
			repaint();
		}
	}

	/**
	 * Handler for dragging mouse.
	 * Moves piece if one is grabbed.
	 * @param e MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (grabbedPiece != null) {
			grabbedPiece.setCoordinates(new Point(e.getX() - grabOffset.x, e.getY() - grabOffset.y));
		}
		repaint();
	}
	
	/********************************************************
	/** the stuff below is for MouseListener, but not used **
	/*******************************************************/ 
	@Override public void mouseClicked(MouseEvent e)  { }		
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
	@Override public void mouseMoved(MouseEvent e) { }
}
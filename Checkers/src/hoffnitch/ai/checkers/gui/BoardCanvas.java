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
	private boolean canMove;

	private volatile Turn newTurn;
	private Turn turnBeingBuilt;
	private List<Iterator<Position>> turnIterators;
	private List<Turn> possibleTurns;
	
	public BoardCanvas(GameState board) {
		guiPieces = new LinkedList<GuiPiece>();
		pieceMap = new HashMap<Piece, GuiPiece>();
		turnIterators = new LinkedList<Iterator<Position>>();
		
		this.board = board;
		
		initializePieces(board);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Turn getTurn(List<Turn> possibleTurns) {
		this.possibleTurns = possibleTurns;
		
		resetGetTurn();
		canMove = true;
		
		// block until newTurn is set
		while (newTurn == null);
		
		return newTurn;
	}
	
	private void resetGetTurn() {
		newTurn = null;
		turnBeingBuilt = null;
		grabbedPiece = null;
		turnBeingBuilt = null;
	}
	
	private void setBaseIterators(Piece piece) {
		turnIterators.clear();
		for (Turn turn: possibleTurns)
			if (turn.piece == piece) {
				Iterator<Position> iterator = turn.iterator();
				turnIterators.add(iterator);
				iterator.next();
			}
	}
	
	private void filterItertors(Position position) {
		for (int i = turnIterators.size() - 1; i >= 0; i--) {
			Iterator<Position> iterator = turnIterators.get(i);
			Position next = iterator.next();
			
			if (!(next.equals(position)))
				turnIterators.remove(i);
		}
	}
	
	/**
	 * Generate a guiPieces based on pieces in gameState
	 * @param board GameState to get pieces from
	 */
	public void initializePieces(GameState board) {
		guiPieces.clear();
		pieceMap.clear();
		
		for (short i = 1; i <= GameState.NUM_POSITIONS; i++) {
			Piece piece = board.getPieceAtPosition(new Position(i));
			if (piece != null) {
				GuiPiece guiPiece = new GuiPiece(piece, TILE_SIZE);
				guiPieces.add(guiPiece);
				pieceMap.put(piece, guiPiece);
			}
		}
		repaint();
	}
	
	public void syncWithGameState() {
		for (GuiPiece piece: guiPieces)
			piece.setCoordinates();
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
		}
	}
	
	/**
	 * Handler for mousePress.
	 * Picks up a piece
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (canMove) {
			int row = e.getY() / TILE_SIZE;
			int column = e.getX() / TILE_SIZE;
			
			if (CheckerBoardLocationLookup.isValidPosition(row, column)) {
				Position position = new Position(row, column);
				
				// if no grabbed piece, select the piece
				if (grabbedPiece == null) {
					Piece piece = board.getPieceAtPosition(position);
					if (piece != null) {
						setBaseIterators(piece);
						
						if (turnIterators.size() > 0) {
							grabbedPiece = pieceMap.get(piece);
							grabbedPiece.setMoving(true);
							grabOffset = new Point(e.getX() % TILE_SIZE, e.getY() % TILE_SIZE);
							turnBeingBuilt = new Turn(grabbedPiece.piece, position);
						}
					}
				}
				
				// otherwise, selecting new position for piece
				else {
					filterItertors(position);
					
					// if no errors
					if (turnIterators.size() > 0) {
						
						turnBeingBuilt.addMove(position);
						
						// if we got to an end point, we are done
						if (turnIterators.size() == 1 && !turnIterators.get(0).hasNext()) {
							
							newTurn = turnBeingBuilt;
							grabbedPiece = null;
							grabOffset = null;
							canMove = false;
						}
						
					}
					
					// if we went to a bad location start over
					else {
						resetGetTurn();
						repaint();
					}
					
				}
			}
		}
	}
	
	/**
	 * Handler for dragging mouse.
	 * Moves piece if one is grabbed.
	 * @param e MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (grabbedPiece != null) {
			grabbedPiece.setCoordinates(new Point(e.getX() - grabOffset.x, e.getY() - grabOffset.y));
		}
		repaint();
	}
	
	/********************************************************
	/** the stuff below is for MouseListener, but not used **
	/*******************************************************/ 
	@Override public void mouseReleased(MouseEvent e)  { }
	@Override public void mouseClicked(MouseEvent e)  { }		
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
	@Override public void mouseDragged(MouseEvent e) { }
}
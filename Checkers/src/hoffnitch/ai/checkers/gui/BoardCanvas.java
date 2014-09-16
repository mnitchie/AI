package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.CheckerBoardLocationLookup;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.RowAndColumn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JComponent;

/**
 * Canvas on which the board is drawn
 */
public class BoardCanvas extends JComponent {
	private static final long serialVersionUID = 7300683785832599182L;
	
	public static final int TILE_SIZE		= 60;
	public static final Color BOARD_BLACK	= Color.GRAY;
	public static final Color BOARD_WHITE	= Color.WHITE;
	
	private List<GuiPiece> guiPieces;
	
	public BoardCanvas() {
		
	}
	
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
	
	/**
	 * Get the coordinates of a position
	 * @param position Position
	 * @return Coordinates
	 */
	public Point getCoordinates(Position position)
	{
		RowAndColumn rowAndColumn = position.getRowAndColumn();
		return new Point(rowAndColumn.column * TILE_SIZE, rowAndColumn.row * TILE_SIZE);
	}
	
	public Point getPositionOffset(int x, int y) {
		return new Point(x % TILE_SIZE, y % TILE_SIZE);
	}
	
}
package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Position;

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
	public static final Color ARROW_COLOR	= Color.CYAN;
	public static final Color INDEX_COLOR	= Color.BLUE;
	
	private List<GuiPiece> guiPieces;
	private List<Arrow> arrows;
	
	public BoardCanvas() {
		
	}
	
	public void repaint(List<GuiPiece> guiPieces) {
		this.guiPieces = guiPieces;
		repaint();
	}
	
	public void setArrows(List<Arrow> arrows)
	{
		this.arrows = arrows;
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
		
		// draw arrows
		g.setColor(ARROW_COLOR);
		if (arrows != null)
			for (Arrow arrow: arrows)
				g.fillPolygon(arrow.toPolygon());
		
		// indices
		drawIndices(g);
	}
	
	/**
	 * Draw the tiles of the board
	 * @param g
	 */
	private void drawBoard(Graphics g) {
		for (short row = 0; row < GameState.WIDTH; row++) {
			for (short col = 0; col < GameState.WIDTH; col++) {
				Color color = ((row + col) % 2 == 0)? BOARD_WHITE: BOARD_BLACK;
				drawTile(g, row, col, color);
			}
		}
	}
	
	/**
	 * Draw tile indices
	 * @param g
	 */
	private void drawIndices(Graphics g) {
		g.setColor(INDEX_COLOR);
		for (short row = 0; row < GameState.WIDTH; row++) {
			for (short col = 0; col < GameState.WIDTH; col++) {
				drawIndex(g, row, col);
			}
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
	 * Draw a tile's index.
	 * This assumes the appropriate color has already been set on g.
	 * @param g Graphics object
	 * @param row Row index
	 * @param col Column index
	 */
	private void drawIndex(Graphics g, short row, short col) {
		if ((row + col) % 2 == 1) {
			int x = col * TILE_SIZE;
			int y = row * TILE_SIZE;
			int index = col / 2 + row * 4 + 1;
			g.setColor(INDEX_COLOR);
			g.drawString("" + index, x + 10, y + 20);
		}
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
		
		return Position.getPosition(row, column);
	}
	
	/**
	 * Get the coordinates of a position
	 * @param position Position
	 * @return Coordinates
	 */
	public Point getCoordinates(Position position) {
		return new Point(position.column * TILE_SIZE, position.row * TILE_SIZE);
	}
	
	public Point getPositionOffset(int x, int y) {
		return new Point(x % TILE_SIZE, y % TILE_SIZE);
	}
	
	public Point getCenter(Position position) {
		return new Point(TILE_SIZE / 2 + position.column * TILE_SIZE,
				TILE_SIZE / 2 + position.row * TILE_SIZE);
	}
	
}
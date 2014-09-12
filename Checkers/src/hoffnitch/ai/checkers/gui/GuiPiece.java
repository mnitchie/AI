package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.RowAndColumn;

import java.awt.Color;
import java.awt.Point;

public class GuiPiece {
	private int size;
	private Point coordinates;
	private boolean isMoving;
	public final Color renderColor;
	public final Piece piece;
	
	public GuiPiece(Piece piece, int size) {
		this.piece = piece;
		this.size = size;
		isMoving = false;
		renderColor = piece.color.getGuiColor();
		setCoordinates();
	}
	
	public void setCoordinates() {
		RowAndColumn rowAndColumn = piece.getPosition().getRowAndColumn();
		coordinates = new Point(size * rowAndColumn.column, size * rowAndColumn.row);
	}

	public Point getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
}

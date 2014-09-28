package hoffnitch.ai.checkers;

import java.awt.Color;

public enum PieceColor {
	
	LIGHT (Color.RED, "Light"),
	DARK (Color.BLACK, "Dark")
	;
	
	public final Color color;
	public final String name;
	
	PieceColor (Color color, String name) {
		this.color = color;
		this.name = name;
	}
	
	public Color getGuiColor() {
		return color;
	}
	
	public String toString() {
		return name;
	}
	
	public static PieceColor opposite(PieceColor color) {
		return (color == PieceColor.LIGHT)? PieceColor.DARK: PieceColor.LIGHT;
	}
}

package hoffnitch.ai.checkers;

import java.awt.Color;

public enum PieceColor {
	LIGHT {
	    public Color getGuiColor() {
	        return Color.WHITE;
	    }
	},
	DARK {
	    public Color getGuiColor() {
	        return Color.BLACK;
	    }
	}
	;
	
	public abstract Color getGuiColor();
}

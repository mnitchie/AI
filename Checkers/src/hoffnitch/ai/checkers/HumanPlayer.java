package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.gui.BoardCanvas;

public class HumanPlayer extends Player
{
	BoardCanvas view;
	
	public HumanPlayer(String name, PieceColor color, BoardCanvas view) {
		super(name, color);
		this.view = view;
	}
}

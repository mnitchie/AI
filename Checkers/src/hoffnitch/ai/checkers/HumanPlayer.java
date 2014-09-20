package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.gui.BoardCanvas;

public class HumanPlayer extends Player
{
	BoardCanvas view;
	
	public HumanPlayer(String name, PieceColor color, BoardCanvas view) {
		super(name, color);
		this.view = view;
	}

    @Override
    public PlayerType getType() {
        return PlayerType.HUMAN;
    }

    @Override
    public int getVersion() {
        return 1;
    }

}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.AIPlayer;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public class RandomBot extends AIPlayer {

	public RandomBot(String name, PieceColor color) {
		super(name, color);
	}

	@Override
	public Turn getTurn(List<Turn> options) {
		int random = (int)Math.floor(Math.random() * options.size());
		return options.get(random);
	}

}

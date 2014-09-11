package hoffnitch.ai.checkers.ai;

import java.util.List;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;

public class RandomBot extends Player {

	public RandomBot(String name, PieceColor color) {
		super(name, color);
	}

	@Override
	public Turn getTurn(List<Turn> options) {
		int random = (int)Math.floor(Math.random() * options.size());
		return options.get(random);
	}

}

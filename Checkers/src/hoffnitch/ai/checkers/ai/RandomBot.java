package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public class RandomBot extends AIPlayer {
    
    private static final String HEURISTIC_DESCRIPTION = "Random Bot";

	public RandomBot(PieceColor color) {
		super(HEURISTIC_DESCRIPTION, color);
	}

	@Override
	public Turn getTurn(List<Turn> options) {
		int random = (int)Math.floor(Math.random() * options.size());
		return options.get(random);
	}
	
	@Override
    public String getName() {
        return HEURISTIC_DESCRIPTION;
    }
}

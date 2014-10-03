package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;
import java.util.Random;

public class RandomBot extends AIPlayer {
    
    private static final String HEURISTIC_DESCRIPTION = "Random Bot";
    public static final Random generator = new Random();

	public RandomBot(PieceColor color) {
		super(HEURISTIC_DESCRIPTION, color);
	}

	@Override
	public Turn getTurn(List<Turn> options) {
		int random = (int)Math.floor(Math.random() * options.size());
		return options.get(random);
	}
	
	@Override
	public double evaluateBoard(GameState board) {
	    boolean negative = generator.nextInt(2) == 1 ? true : false;
	    double num = generator.nextDouble();
	    return negative ? -num : num;
	}
	
	@Override
    public String getName() {
        return HEURISTIC_DESCRIPTION;
    }
}

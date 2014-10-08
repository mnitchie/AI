package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;
import java.util.Random;

public class RandomBot extends AIPlayer {
    
    public static final String HEURISTIC_DESCRIPTION = "Random Bot";
    private static final Random generator = new Random();

	public RandomBot(PieceColor color) {
		super(HEURISTIC_DESCRIPTION, color);
	}
	
	@Override
	public double evaluateBoard(GameState board) {
	    boolean negative = generator.nextInt(2) == 1 ? true : false;
	    double num = generator.nextDouble();
	    return negative ? -num : num;
	}
}

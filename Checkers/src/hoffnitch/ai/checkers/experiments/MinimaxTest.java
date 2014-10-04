package hoffnitch.ai.checkers.experiments;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.ai.AIPlayer;
import hoffnitch.ai.checkers.ai.RandomBot;
import hoffnitch.ai.search.CheckersTree;

public class MinimaxTest {
    public static void main(String[] args) {
        GameState board = new GameState();
        AIPlayer randomDark = new RandomBot(PieceColor.DARK);
        
        CheckersTree tree = new CheckersTree(board, PieceColor.DARK, 6);
        System.out.println("Wait here...");
        
        tree.evaluateNodes(randomDark);
        System.out.println("And here...");
        
        Turn t = tree.getBestTurn();
        System.out.println(t);

    }
}

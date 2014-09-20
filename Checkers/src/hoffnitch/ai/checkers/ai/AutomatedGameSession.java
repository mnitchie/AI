package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.CheckersTurnMoveGenerator;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public class AutomatedGameSession {
    
    private AIPlayer darkPlayer;
    private AIPlayer lightPlayer;
    private CheckersTurnMoveGenerator moveGenerator;
    private int darkWins;
    private int lightWins;
    private int draws;
    private int numGames = 1000;
    
    private static final int DRAW_THRESHHOLD = 500;
    
    public AutomatedGameSession(AIPlayer darkPlayer, AIPlayer lightPlayer) {
        this.darkPlayer = darkPlayer;
        this.lightPlayer = lightPlayer;
        moveGenerator = new CheckersTurnMoveGenerator();
    }
    
    public AutomatedGameSession(AIPlayer darkPlayer, AIPlayer lightPlayer, int numGames) {
        this(darkPlayer, lightPlayer);
        this.numGames = numGames;
    }
    
    public void play() {
        List<Turn> moves;
        Turn turn;
        GameState board;
        
        Long start = System.currentTimeMillis();
        for (int i = 0; i < numGames; i++) {
            System.out.print("Game " + (i + 1) + ": ");
            int numMoves = 0;
            board = new GameState();
            while (true) {
                if (numMoves > DRAW_THRESHHOLD) {
                    draws++;
                    System.out.println("Draw");
                    break;
                }
                
                moves = moveGenerator.getMovesForTurn(PieceColor.DARK, board);
                if (moves.size() == 0) {
                    System.out.println("Light wins");
                    lightWins++;
                    break;
                }
                turn = darkPlayer.getTurn(moves);
                board.doTurn(turn);
                numMoves++;
                
                moves = moveGenerator.getMovesForTurn(PieceColor.LIGHT, board);
                if (moves.size() == 0) {
                    darkWins++;
                    System.out.println("Dark wins");
                    break;
                }
                turn = lightPlayer.getTurn(moves);
                board.doTurn(turn);
                numMoves++;
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println(numGames + " games took " + (end - start) + " ms");
        System.out.println("Dark won: " + darkWins);
        System.out.println("Light won: " + lightWins);
        System.out.println("Draws: " + draws);
    }
}

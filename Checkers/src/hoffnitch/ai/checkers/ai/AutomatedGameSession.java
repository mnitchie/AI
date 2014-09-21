package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.CheckersTurnMoveGenerator;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.db.DBConnection;
import hoffnitch.ai.checkers.db.Game;
import hoffnitch.ai.checkers.db.GameResult;

import java.util.List;

public class AutomatedGameSession {
    
    private AIPlayer darkPlayer;
    private AIPlayer lightPlayer;
    private CheckersTurnMoveGenerator moveGenerator;
    private int darkWins;
    private int lightWins;
    private int draws;
    private int numGames = 1000;
    
    private static DBConnection db = new DBConnection();
    
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
        Game game;
        
        Long start = System.currentTimeMillis();
        for (int i = 0; i < numGames; i++) {
            int numMoves = 0;
            board = new GameState();
            game = new Game(darkPlayer.getName(), lightPlayer.getName());
            while (true) {
                if (numMoves > DRAW_THRESHHOLD) {
                    draws++;
                    game.setResult(GameResult.DRAW);
                    break;
                }
                
                moves = moveGenerator.getMovesForTurn(PieceColor.DARK, board);
                if (moves.size() == 0) {
                    lightWins++;
                    game.setResult(GameResult.LIGHT_WIN);
                    break;
                }
                turn = darkPlayer.getTurn(moves);
                board.doTurn(turn);
                game.addTurn(turn);
                numMoves++;
                
                moves = moveGenerator.getMovesForTurn(PieceColor.LIGHT, board);
                if (moves.size() == 0) {
                    darkWins++;
                    game.setResult(GameResult.DARK_WIN);
                    break;
                }
                turn = lightPlayer.getTurn(moves);
                board.doTurn(turn);
                game.addTurn(turn);
                numMoves++;
            }
            db.save(game);
        }
        Long end = System.currentTimeMillis();
        System.out.println(numGames + " games took " + (end - start) + " ms");
        System.out.println(darkPlayer.name + " won: " + darkWins);
        System.out.println(lightPlayer.name + " won: " + lightWins);
        System.out.println("Draws: " + draws);
    }
}

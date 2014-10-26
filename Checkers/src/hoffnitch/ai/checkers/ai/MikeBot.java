package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.Turn;

public class MikeBot extends AIPlayer {

    public static final String HEURISTIC_DESCRIPTION = "Mike Bot";
    private double kingWeight;
    private Turn previousTurn;
    private float previousTurnScore;
    private int maxScript;
    
    private String[] darkScript = {
            "11-15",
            "8-11",
            "4-8",
            "10-14",
            "14-23",
            "7-14",
            "6-10"
    };
    private String[] lightScript = {
            "23-18",
            "27-23",            
            "23-19",
            "19-10",
            "26-19",
            "24-20",
            "22-17"
    }; 
    
//    private int[] darkPawnScores = {
//            7,7,7,7,
//            1,1,1,1,
//            2,2,2,2,
//            3,3,3,3,
//            4,4,4,4,
//            5,5,5,5,
//            6,6,6,6,
//            7,7,7,7
//    };
//    
//    private int[] darkKingScores = {
//            7,7,7,7,
//            6,6,6,6,
//            5,5,5,5,
//            4,4,4,4,
//            3,3,3,3,
//            2,2,2,2,
//            3,3,3,3,
//            0,0,0,0
//    };
    
    int turnCount = 0;
    
    public MikeBot(PieceColor color, double kingWeight) {
        super(HEURISTIC_DESCRIPTION, color);
        this.kingWeight = kingWeight;
        maxScript = color.equals(PieceColor.DARK) ? 3 : 0;
    }
    
    @Override
    public double evaluateBoard(GameState board) {
        double score = 0;
        score += scoreBoardOnPieceCount(board, kingWeight);
        score += scoreBoardOnKingsInMiddle(board);
        score += scoreBoardOnPawnsNearPromotion(board);
        
//        if (score == 0) {
//            score += scoreBoardOnPositions(board);
//        }
        
        return score;
    }
    
    public double scoreBoardOnPawnsNearPromotion(GameState board) {
        double score = 0;
        for (Piece p : board.getPieces(getColor())) {
            if (getColor().equals(PieceColor.DARK) && !p.isCrowned() && p.getPosition().index % 8 == 6) {
//                score += .3;
            } else if (getColor().equals(PieceColor.LIGHT ) && !p.isCrowned() && p.getPosition().index % 8 == 1) {
                score += .3;
            }
            
        }
        
        return score;
    }
    
    public double scoreBoardOnKingsInMiddle(GameState board) {
        double score = 0;
        for (Piece p : board.getPieces(getColor())) {
            if (getColor().equals(PieceColor.DARK) && p.isCrowned() && p.getPosition().index % 8 == 5) {
//                score += .1;
            } else if (getColor().equals(PieceColor.LIGHT ) && p.isCrowned() && p.getPosition().index % 8 == 2) {
                score += .1;
            }
            
        }
        
        return score;
    }
    
    @Override
    public Turn getTurn() {
        System.out.println("Getting turns");
        turnCount++;
        if (turnCount <= maxScript) {
            System.out.println("turn count is < 7");
            int start;
            int end;
            if (this.getColor().equals(PieceColor.DARK)) {
                start = Integer.parseInt(darkScript[turnCount-1].split("-")[0]);
                end = Integer.parseInt(darkScript[turnCount-1].split("-")[1]);
            } else {
                start = Integer.parseInt(lightScript[turnCount-1].split("-")[0]);
                end = Integer.parseInt(lightScript[turnCount-1].split("-")[1]);
            }   
            System.out.println(start);
            System.out.println(end);
            Turn theTurn = new Turn(start, this.getColor(), Position.getPosition(start));
            theTurn.addMove(Position.getPosition(end));
            if (turnTree.turnIsAvailable(theTurn)) {
                System.out.println("The turn is available");
                previousTurn = turnTree.getBestTurn();
                return previousTurn;
            }
        }
        evaluateTurns();
        turnTree.preventInverse(previousTurn);
        previousTurn = turnTree.getBestTurn();
        return previousTurn;
    }
}

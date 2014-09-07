package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public class CheckersMoveGenerator {
    private GameState gameState;
    
    public CheckersMoveGenerator(GameState gameState) {
        this.gameState = gameState;
    }
    
    public List<Turn> getMovesForTurn(PieceColor pieceColor) {
        List<Turn> legalTurns = new ArrayList<Turn>();
        CheckerPieceOptionTree options;
        for (int i = 1; i <= GameState.NUM_POSITIONS; i++) {
            Piece piece = gameState.getPieceAtPosition(i);
            if (piece != null && piece.color == pieceColor) {
                options = new CheckerPieceOptionTree(piece);
                legalTurns.addAll(options.getMoves(gameState));
            }
        }
        return legalTurns;
    }
}
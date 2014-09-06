package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public class CheckersMoveGenerator {

    private final GameState gameState;

    public CheckersMoveGenerator(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Turn> getMovesForTurn() {
        List<Turn> legalMoves = new ArrayList<Turn>();

        for (int i = 1; i <= 32; i++) {
            Piece piece = gameState.getPieceAtPosition(i);
            if (piece != null) {
                legalMoves.addAll(getMovesForPiece(piece));
            }
        }

        return null;
    }

    /**
     * <a href="http://www.bobnewell.net/nucleus/rsd.php?itemid=289"></a> 
     * <ahref="https://github.com/mnitchie/AI/blob/master/Checkers/Notes/MoveLogic.md"></a>
     */
    private List<Turn> getMovesForPiece(Piece piece) {
        List<Turn> legalTurnsForPiece = new ArrayList<Turn>();
        List<Position> positionsToCheck = new ArrayList<Position>();
        RowAndColumn rc = piece.getPosition().getRowAndColumn();

        Position topLeft = new Position(rc.row - 1, rc.column - 1);
        Position topRight = new Position(rc.row - 1, rc.column + 1);
        Position bottomLeft = new Position(rc.row + 1, rc.column - 1);
        Position bottomRight = new Position(rc.row + 1, rc.column + 1);

        if (isInBounds(topLeft)
                && (piece.isCrowned() || piece.color == PieceColor.WHITE)) {
            positionsToCheck.add(topLeft);
        }

        if (isInBounds(topRight)
                && (piece.isCrowned() || piece.color == PieceColor.WHITE)) {
            positionsToCheck.add(topRight);
        }

        if (isInBounds(bottomLeft)
                && (piece.isCrowned() || piece.color == PieceColor.BLACK)) {
            positionsToCheck.add(bottomLeft);
        }

        if (isInBounds(bottomLeft)
                && (piece.isCrowned() || piece.color == PieceColor.BLACK)) {
            positionsToCheck.add(bottomRight);
        }
        
        for (Position p : positionsToCheck) {
            Piece adjacentPiece = gameState.getPieceAtPosition(p);
            if (adjacentPiece == null)  {
                legalTurnsForPiece.add(new Turn(piece, p));
            } else if (adjacentPiece.color.equals(piece.color)) {
                // Overtake
            }
        }
        return legalTurnsForPiece;
    }

    private boolean isInBounds(Position p) {
        return p.getIndex() <= GameState.MAX_INDEX;
    }
}

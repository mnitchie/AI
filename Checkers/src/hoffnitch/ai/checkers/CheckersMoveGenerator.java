package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public class CheckersMoveGenerator {

    private final GameState gameState;

    public CheckersMoveGenerator(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Turn> getMovesForTurn(PieceColor pieceColor) {
        List<Turn> legalMoves = new ArrayList<Turn>();

        for (int i = 1; i <= 32; i++) {
            Piece piece = gameState.getPieceAtPosition(i);
            if (piece != null && piece.color == pieceColor) {
                legalMoves.addAll(getMovesForPiece(piece));
            }
        }

        return legalMoves;
    }

    /**
     * <a href="http://www.bobnewell.net/nucleus/rsd.php?itemid=289"></a> 
     * <ahref="https://github.com/mnitchie/AI/blob/master/Checkers/Notes/MoveLogic.md"></a>
     */
    private List<Turn> getMovesForPiece(Piece piece) {
        List<Turn> legalTurnsForPiece = new ArrayList<Turn>();
        List<Position> positionsToCheck = new ArrayList<Position>();
        RowAndColumn rc = piece.getPosition().getRowAndColumn();

        RowAndColumn topLeft = new RowAndColumn(rc.row - 1, rc.column - 1);
        RowAndColumn topRight = new RowAndColumn(rc.row - 1, rc.column + 1);
        RowAndColumn bottomLeft = new RowAndColumn(rc.row + 1, rc.column - 1);
        RowAndColumn bottomRight = new RowAndColumn(rc.row + 1, rc.column + 1);

        if (isInBounds(topLeft)
                && (piece.isCrowned() || piece.color == PieceColor.WHITE)) {
            positionsToCheck.add(new Position(topLeft));
        }

        if (isInBounds(topRight)
                && (piece.isCrowned() || piece.color == PieceColor.WHITE)) {
            positionsToCheck.add(new Position(topRight));
        }

        if (isInBounds(bottomLeft)
                && (piece.isCrowned() || piece.color == PieceColor.BLACK)) {
            positionsToCheck.add(new Position(bottomLeft));
        }

        if (isInBounds(bottomRight)
                && (piece.isCrowned() || piece.color == PieceColor.BLACK)) {
            positionsToCheck.add( new Position(bottomRight));
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
    
    private boolean isInBounds(RowAndColumn rc) {
        return rc.row < GameState.WIDTH &&
               rc.column < GameState.WIDTH &&
               rc.row >= 0 &&
               rc.column >= 0;
    }
}

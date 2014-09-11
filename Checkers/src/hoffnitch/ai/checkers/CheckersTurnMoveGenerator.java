package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public class CheckersTurnMoveGenerator {
    
    private GameState gameState;
    private List<Turn> jumpMovesForTurn;
    private List<Turn> adjacentMovesForTurn;
    
    public CheckersTurnMoveGenerator(GameState gameState) {
        this.gameState = gameState;
    }
    
    public List<Turn> getMovesForTurn(PieceColor pieceColor) {
        jumpMovesForTurn = new ArrayList<Turn>();
        adjacentMovesForTurn = new ArrayList<Turn>();
        for (int i = 1; i <= GameState.NUM_POSITIONS; i++) {
            Piece piece = gameState.getPieceAtPosition(i);
            if (piece != null && piece.color == pieceColor) {
                generateJumpMovesForPiece(piece, piece.getPosition(), gameState, new ArrayList<Position>());
                // Only look for adjacent moves if we don't have any jump moves yet.
                if (jumpMovesForTurn.isEmpty()) {
                    generateAdjacentMovesForPiece(piece);
                }                
            }
        }
        
        if (!jumpMovesForTurn.isEmpty()) {
            return jumpMovesForTurn;
        } 
        List<Turn> allMoves = new ArrayList<Turn>();
        allMoves.addAll(jumpMovesForTurn);
        allMoves.addAll(adjacentMovesForTurn);
        return allMoves;
    }
    
    public void generateJumpMovesForPiece(Piece piece, Position position, GameState gamestate, List<Position> moves) {
        for (Direction direction : Direction.values()) {
            if (pieceCanJumpInDirection(piece, position, direction, gameState)) {
                GameState copy = new GameState(gameState);
                // for clearing the jumped piece from the copied game state
                Position adjacentPosition = position
                        .getPositionInDirection(direction);
                // for placing the jumping piece after the piece is executed
                Position farPosition = adjacentPosition
                        .getPositionInDirection(direction);
                copy.setPiece(null, position);
                copy.setPiece(null, adjacentPosition);
                copy.setPiece(new Piece(piece), farPosition);
                List<Position> movesForPiece = new ArrayList<Position>(moves);
                movesForPiece.add(position);
                generateJumpMovesForPiece(piece, farPosition, copy, movesForPiece);
            } 
            
        }
        if (!moves.isEmpty()) {
            moves.add(position);
            jumpMovesForTurn.add(new Turn(piece, moves));
        }
    }
    
    private boolean pieceCanJumpInDirection(Piece piece, Position position,
            Direction direction, GameState gameState) {

        // the piece can move in that direction
        if (!direction.pieceCanMove(piece)) {
            return false;
        }
        RowAndColumn adjacentRC = position.getRowAndColumn()
                .getRowAndColumnInDirection(direction);
        // if the adjacent place is in bounds
        if (!isInBounds(adjacentRC)) {
            return false;
        }
        Piece adjacentPiece = gameState.getPieceAtPosition(adjacentRC);
        // the position in that direction is occupied
        if (adjacentPiece == null) {
            return false;
        }
        // the piece in that position is an opponent
        if (adjacentPiece.color == piece.color) {
            return false;
        }
        RowAndColumn farRC = adjacentRC.getRowAndColumnInDirection(direction);
        // if the far position is valid
        if (!isInBounds(farRC)) {
            return false;
        }
        // if the far position is available
        Piece farPiece = gameState.getPieceAtPosition(farRC);
        if (farPiece != null) {
            return false;
        }

        return true;
    }
    
    private boolean isInBounds(RowAndColumn rc) {
        return rc.row < GameState.WIDTH 
                && rc.column < GameState.WIDTH
                && rc.row >= 0 
                && rc.column >= 0;
    }
    
    private void generateAdjacentMovesForPiece(Piece piece) {
        for (Direction direction : Direction.values()) {
            if (pieceCanMoveInDirection(piece, direction)) {
                Turn turn = new Turn(piece);
                turn.addMove(piece.getPosition());
                turn.addMove(piece.getPosition().getPositionInDirection(direction));
                adjacentMovesForTurn.add(turn);
            }
        }
    }
    
    private boolean pieceCanMoveInDirection(Piece piece, Direction direction) {
        if (!direction.pieceCanMove(piece)) {
            return false;
        }
        
        RowAndColumn adjacentRC = piece.getPosition().getRowAndColumn().getRowAndColumnInDirection(direction);
        if (!isInBounds(adjacentRC)) {
            return false;
        }
        
        Piece adjacentPiece = gameState.getPieceAtPosition(adjacentRC);
        if (adjacentPiece != null) {
            return false;
        }
        
        return true;
    }
}
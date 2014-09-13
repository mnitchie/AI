package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public class CheckersTurnMoveGenerator {
    
    private GameState gameState;
    private List<Turn> jumpMovesForTurn;
    private List<Turn> adjacentMovesForTurn;
    
    public CheckersTurnMoveGenerator(GameState gameState) {
        //this.gameState = gameState;
    }
    
    public List<Turn> getMovesForTurn(PieceColor pieceColor, GameState board) {
    	gameState = board;
    	
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
        
        // If there are any jump moves, return only the jump moves
        if (!jumpMovesForTurn.isEmpty()) {
            return jumpMovesForTurn;
        } 
        
        List<Turn> allMoves = new ArrayList<Turn>();
        allMoves.addAll(jumpMovesForTurn);
        allMoves.addAll(adjacentMovesForTurn);
        return allMoves;
    }
    
    /**
     * Algorithm: <pre>
     * For each direction
     *   IF piece can jump in that direction<br />
     *     Copy gameState and change the copy to reflect how the board would
     *     look after the jump move
     *     add that move to the list of possible moves
     *     execute the same algorithm from the new position with the new gameState
     *   ELSE
     *     IF there were any possible moves
     *       create a new Turn with piece and the list of moves</pre>
     * @param piece The Piece object making the move. Will not change during a recursive call
     * @param position The current Position of the piece. Will change during recursive calls
     * @param gamestate The current game state. Will change to reflect the removal of jumped pieces
     * @param moves The list of positions piece has occupied during the jump sequence. Will change during recursive calls.
     */
    public void generateJumpMovesForPiece(Piece piece, Position position, GameState currentGameState, List<Position> moves) {
        boolean hasOption = false;
        for (Direction direction : Direction.values()) {
            if (pieceCanJumpInDirection(piece, position, direction, currentGameState)) {
                hasOption = true;
                GameState copy = new GameState(currentGameState);
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
        if (!hasOption && !moves.isEmpty()) {
            moves.add(position);
            jumpMovesForTurn.add(new Turn(piece, moves));
        }
    }
    
    /**
     * Checks if a given piece can move in a given direction
     * @param piece The piece being moved
     * @param position The position piece currently occupies
     * @param direction The direction to move
     * @param gameState The current gameState
     * @return true of the piece can jump in the given direction. false otherwise.
     */
    private boolean pieceCanJumpInDirection(Piece piece, Position position,
            Direction direction, GameState currentGameState) {

        // the piece can move in that direction
        if (!direction.pieceCanMove(piece)) {
            return false;
        }
        
        // if the adjacent place is in bounds
        RowAndColumn adjacentRC = position.getRowAndColumn()
                .getRowAndColumnInDirection(direction);
        if (!isInBounds(adjacentRC)) {
            return false;
        }
        
        // the position in that direction is occupied
        Piece adjacentPiece = currentGameState.getPieceAtPosition(adjacentRC);
        if (adjacentPiece == null) {
            return false;
        }
        
        // the piece in that position is an opponent
        if (adjacentPiece.color == piece.color) {
            return false;
        }
        
        // if the far position is valid
        RowAndColumn farRC = adjacentRC.getRowAndColumnInDirection(direction);
        if (!isInBounds(farRC)) {
            return false;
        }
        
        // if the far position is available
        Piece farPiece = currentGameState.getPieceAtPosition(farRC);
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
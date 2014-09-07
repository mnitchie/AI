package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckerPieceOptionTree {
    
    private class Node {
        private Position position;
        private Piece piece;
        private Map<Direction, Node> children;
        
        public Node(Position position, Piece piece) {
            this.position = position;
            this.piece = piece;
            children = new HashMap<Direction, Node>();
            for (Direction d : Direction.values()) {
                children.put(d, null);
            }
        }
        
        public boolean isLeaf() {
            return children.get(Direction.TOP_LEFT) == null &&
                   children.get(Direction.TOP_RIGHT) == null &&
                   children.get(Direction.BOTTOM_LEFT) == null &&
                   children.get(Direction.BOTTOM_RIGHT) == null;
        }
    }
    
    private Node root;
    private List<Turn> movesForPiece = new ArrayList<Turn>();
    
    public CheckerPieceOptionTree(Piece piece) {
        this.root = new Node(piece.getPosition(), piece);
    }
    
    public List<Turn> getMoves(GameState gameState) {
        populateMoves(gameState);
        if (root.isLeaf()) {
            return Collections.emptyList();
        }
        doGetMoves(root, root.piece);
        return movesForPiece;
    }
    
    private Turn doGetMoves(Node n, Piece startingPiece) {
        if (n.isLeaf()) {
            return new Turn(startingPiece, n.position);
        }
        for (Direction direction : Direction.values()) {
            Node next = n.children.get(direction);
            if (next != null) {
                if (n == root) {
                    movesForPiece.add(doGetMoves(next, startingPiece));
                } else {
                    movesForPiece.add(doGetMoves(next, startingPiece).addMove(n.position));
                }
            }
        }
        return null; //KLUDGE!!!
        
    }
    
    private void populateMoves(GameState gameState) {
        generateJumpMoves(root, gameState);
        if (root.isLeaf()) {
            generateAdjacentMoves(root, gameState);
        }
    }
    
    private void generateJumpMoves(Node n, GameState gameState) {
        for (Direction direction : Direction.values()) {
            if (pieceCanJumpInDirection(n.piece, direction, gameState)) {
                Node move = new Node(n.position.getPositionInDirection(direction), n.piece);
                n.children.put(direction, move);
                GameState copy = new GameState(gameState);
                Position adjacentPosition = n.position.getPositionInDirection(direction);
                Position farPosition = adjacentPosition.getPositionInDirection(direction);
                copy.setPiece(null, n.position);
                copy.setPiece(null, adjacentPosition);
                copy.setPiece(n.piece, farPosition);
                generateJumpMoves(move, copy);
            }
        }
    }
    
    private void generateAdjacentMoves(Node n, GameState gameState) {
        for (Direction direction : Direction.values()) {
            RowAndColumn adjacentRC = n.position.getRowAndColumn().getRowAndColumnInDirection(direction);
            if (isInBounds(adjacentRC) && direction.pieceCanMove(n.piece)) {
                if (gameState.getPieceAtPosition(adjacentRC) == null) {
                    n.children.put(direction, new Node(new Position(adjacentRC), n.piece));
                }
            }
        }
    }
    
    private boolean pieceCanJumpInDirection(Piece piece, Direction direction, GameState gameState) {
        
        // the piece can move in that direction
        if (!direction.pieceCanMove(piece)) {
            return false;
        }
        RowAndColumn adjacentRC = piece.getPosition().getRowAndColumn().getRowAndColumnInDirection(direction);
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
        return rc.row < GameState.WIDTH &&
               rc.column < GameState.WIDTH &&
               rc.row >= 0 &&
               rc.column >= 0;
    }
}

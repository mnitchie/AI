package hoffnitch.ai.checkers;

public enum Direction {
    TOP_LEFT(-1, -1) {
        public boolean pieceCanMove(Piece p) {
            return p.isCrowned() || p.color == PieceColor.WHITE;
        }},
    TOP_RIGHT(-1, 1) {
        public boolean pieceCanMove(Piece p) {
            return p.isCrowned() || p.color == PieceColor.WHITE;
        }},
    BOTTOM_LEFT(1, -1) {
        public boolean pieceCanMove(Piece p) {
            return p.isCrowned() || p.color == PieceColor.BLACK;
        }},
    BOTTOM_RIGHT(1, 1) {
        public boolean pieceCanMove(Piece p) {
            return p.isCrowned() || p.color == PieceColor.BLACK;
        }}
    ;
    
    public final int rowAdjustment;
    public final int columnAdjustment;
    
    Direction(int rowAdjustment, int columnAdjustment) {
        this.rowAdjustment = rowAdjustment;
        this.columnAdjustment = columnAdjustment;
    }

    public abstract boolean pieceCanMove(Piece p);
}

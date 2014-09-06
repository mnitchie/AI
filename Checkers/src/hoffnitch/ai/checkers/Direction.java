package hoffnitch.ai.checkers;

public enum Direction {
    TOP_LEFT(-1, -1),
    TOP_RIGHT(-1, 1),
    BOTTOM_LEFT(1, -1),
    BOTTOM_RIGHT(1, 1),
    NONE(0, 0)
    ;
    
    public final int rowAdjustment;
    public final int columnAdjustment;
    
    Direction(int rowAdjustment, int columnAdjustment) {
        this.rowAdjustment = rowAdjustment;
        this.columnAdjustment = columnAdjustment;
    }
    
//    public static Direction getDirection(Position start, Position end) {
//        int rowDiff = start.getRowAndColumn().row - end.getRowAndColumn().row;
//        int colDiff = start.getRowAndColumn().column - end.getRowAndColumn().column;
//        
//        if (rowDiff == 0 || colDiff == 0) {
//            return NONE;
//        }
//        
//        if (rowDiff < 0) {
//            
//        }
//        if (colDiff > 0) {
//            
//        }
//    }
}

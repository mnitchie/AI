package hoffnitch.ai.checkers;

public class RowAndColumn {
    public final int row;
    public final int column;
    
    public RowAndColumn(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public RowAndColumn getRowAndColumnInDirection(Direction direction) {
        return new RowAndColumn(this.row + direction.rowAdjustment, this.column + direction.columnAdjustment);
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof RowAndColumn) {
    		RowAndColumn other = (RowAndColumn) obj;
    		if (row == other.row && column == other.column)
    			return true;
    		else
    			return false;
    	} else {
    		return false;
    	}
    }
    
    @Override
    public int hashCode() {
    	return row * 8 + column;
    }
    
    @Override
    public String toString() {
    	return "(row:" + row + "; col:" + column + ")";
    }
}

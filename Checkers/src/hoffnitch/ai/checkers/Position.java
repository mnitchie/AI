package hoffnitch.ai.checkers;

public class Position {
    private int index;
    private RowAndColumn rowAndColumn;
    
    public Position(int index) {
        this.setLocation(index);
    }
    
    public Position(int row, int column) {
        this.setLocation(row, column);
    }
    
    public Position(RowAndColumn rowAndColumn) {
        this.setLocation(rowAndColumn);
    }
    
    public int getIndex() {
        return index;
    }
    
    public RowAndColumn getRowAndColumn() {
        return rowAndColumn;
    }
    
    public void setLocation(int index) {
        if (index < 1 || index > 32) {
            throw new IllegalArgumentException("Index out of range");
        }
        this.index = index;
        this.rowAndColumn =  CheckerBoardLocationLookup.getLocationFor(index);
    }
    
    public void setLocation(int row, int column) {
        this.rowAndColumn = new RowAndColumn(row, column);
        setIndexFromRowAndColumn();
    }
    
    public void setLocation(RowAndColumn rowAndColumn) {
        this.rowAndColumn = rowAndColumn;
        setIndexFromRowAndColumn();
    }
    
    private void setIndexFromRowAndColumn() {
        this.index = CheckerBoardLocationLookup.getLocationFor(this.rowAndColumn);

    }
}

package hoffnitch.ai.checkers;

public class RowAndColumn {
    public final int row;
    public final int column;
    
    public RowAndColumn(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // TODO: Eclipse default implementation. Consider revising
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + row;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RowAndColumn other = (RowAndColumn) obj;
        if (column != other.column) {
            return false;
        }
        if (row != other.row) {
            return false;
        }
        return true;
    }
}

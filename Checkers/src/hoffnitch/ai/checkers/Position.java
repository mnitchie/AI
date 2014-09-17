package hoffnitch.ai.checkers;

public class Position
{
	public final int index;
	public final int row;
	public final int column;
	
	private static Position[] positions = new Position[32];
	static {
		int index = 0;
		for (int row = 0; row < 8; row++)
			for (int col = 1 - row % 2; col < 8; col += 2) {
				positions[index] = new Position(index + 1, row, col);
				index++;
			}
	}
	
	private Position(int index, int row, int column) {
		this.index = index;
		this.row = row;
		this.column = column;
	}
	
	public static Position getPosition(int index) {
		if (index < 1 || index > 32)
			return null;
		else
			return positions[index - 1];
	}
	
	public static Position getPosition(int row, int column) {
		if (row < 0 || row >= 8
				|| column < 0 || column >= 8
				|| (row + column) % 2 == 0) {
			return null;
		} else {
			return positions[row * 4 + column / 2];
		}
	}
	
	public Position getOffsetPosition(int row, int column) {
		return getPosition(this.row + row, this.column + column);
	}
	
	public Position getOffsetPosition(Direction direction) {
		return getPosition(direction.rowAdjustment + row, direction.columnAdjustment + column);
	}
	
	public String toString() {
		return index + ":(" + row + "," + column + ")";
	}
}
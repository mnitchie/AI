package hoffnitch.ai.checkers;

public class Position2
{
	public final int index;
	public final int row;
	public final int column;
	
	private static Position2[] positions = new Position2[32];
	static {
		int index = 0;
		for (int row = 0; row < 8; row++)
			for (int col = 1 - row % 2; col < 8; col += 2) {
				positions[index] = new Position2(index + 1, row, col);
				index++;
			}
	}
	
	private Position2(int index, int row, int column) {
		this.index = index;
		this.row = row;
		this.column = column;
	}
	
	public static Position2 getPosition(int index) {
		if (index < 1 || index > 32)
			return null;
		else
			return positions[index - 1];
	}
	
	public static Position2 getPosition(int row, int column) {
		if (row < 0 || row >= 8
				|| column < 0 || column >= 8
				|| (row + column) % 2 == 0) {
			return null;
		} else {
			return positions[row * 4 + column / 2];
		}
	}
	
	public String toString() {
		return index + ":(" + row + "," + column + ")";
	}
}
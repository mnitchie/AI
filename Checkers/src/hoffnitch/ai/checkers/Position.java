package hoffnitch.ai.checkers;

public class Position {

	public final short row;
	public final short col;
	
	public Position(short row, short col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Get another position, a given distance away from the current position
	 * @param row Distance between rows
	 * @param col Distance between cols
	 * @return
	 */
	public Position offset(short row,  short col) {
		return new Position((short)(this.row + row), (short)(this.col + col));
	}
}

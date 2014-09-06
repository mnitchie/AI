package hoffnitch.ai.checkers;

import java.util.HashMap;
import java.util.Map;

public class CheckerBoardLocationLookup {
    private static final Map<Integer, RowAndColumn> INDEX_TO_ROW_AND_COLUMN;
    static {
        INDEX_TO_ROW_AND_COLUMN = new HashMap<Integer, RowAndColumn>();
        INDEX_TO_ROW_AND_COLUMN.put(1, new RowAndColumn(0, 1));
        INDEX_TO_ROW_AND_COLUMN.put(2, new RowAndColumn(0, 3));
        INDEX_TO_ROW_AND_COLUMN.put(3, new RowAndColumn(0, 5));
        INDEX_TO_ROW_AND_COLUMN.put(4, new RowAndColumn(0, 7));
        INDEX_TO_ROW_AND_COLUMN.put(5, new RowAndColumn(1, 0));
        INDEX_TO_ROW_AND_COLUMN.put(6, new RowAndColumn(1, 2));
        INDEX_TO_ROW_AND_COLUMN.put(7, new RowAndColumn(1, 4));
        INDEX_TO_ROW_AND_COLUMN.put(8, new RowAndColumn(1, 6));
        INDEX_TO_ROW_AND_COLUMN.put(9, new RowAndColumn(2, 1));
        INDEX_TO_ROW_AND_COLUMN.put(10, new RowAndColumn(2, 3));
        INDEX_TO_ROW_AND_COLUMN.put(11, new RowAndColumn(2, 5));
        INDEX_TO_ROW_AND_COLUMN.put(12, new RowAndColumn(2, 7));
        INDEX_TO_ROW_AND_COLUMN.put(13, new RowAndColumn(3, 0));
        INDEX_TO_ROW_AND_COLUMN.put(14, new RowAndColumn(3, 2));
        INDEX_TO_ROW_AND_COLUMN.put(15, new RowAndColumn(3, 4));
        INDEX_TO_ROW_AND_COLUMN.put(16, new RowAndColumn(3, 6));
        INDEX_TO_ROW_AND_COLUMN.put(17, new RowAndColumn(4, 1));
        INDEX_TO_ROW_AND_COLUMN.put(18, new RowAndColumn(4, 3));
        INDEX_TO_ROW_AND_COLUMN.put(19, new RowAndColumn(4, 5));
        INDEX_TO_ROW_AND_COLUMN.put(20, new RowAndColumn(4, 7));
        INDEX_TO_ROW_AND_COLUMN.put(21, new RowAndColumn(5, 0));
        INDEX_TO_ROW_AND_COLUMN.put(22, new RowAndColumn(5, 2));
        INDEX_TO_ROW_AND_COLUMN.put(23, new RowAndColumn(5, 4));
        INDEX_TO_ROW_AND_COLUMN.put(24, new RowAndColumn(5, 6));
        INDEX_TO_ROW_AND_COLUMN.put(25, new RowAndColumn(6, 1));
        INDEX_TO_ROW_AND_COLUMN.put(26, new RowAndColumn(6, 3));
        INDEX_TO_ROW_AND_COLUMN.put(27, new RowAndColumn(6, 5));
        INDEX_TO_ROW_AND_COLUMN.put(28, new RowAndColumn(6, 7));
        INDEX_TO_ROW_AND_COLUMN.put(29, new RowAndColumn(7, 0));
        INDEX_TO_ROW_AND_COLUMN.put(30, new RowAndColumn(7, 2));
        INDEX_TO_ROW_AND_COLUMN.put(31, new RowAndColumn(7, 4));
        INDEX_TO_ROW_AND_COLUMN.put(32, new RowAndColumn(7, 6));
    }
    private static final Map<RowAndColumn, Integer> ROW_AND_COLUMN_TO_INDEX;
    static {
        ROW_AND_COLUMN_TO_INDEX = new HashMap<RowAndColumn, Integer>();
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(0, 1), 1);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(0, 3), 2);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(0, 5), 3);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(0, 7), 4);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(1, 0), 5);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(1, 2), 6);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(1, 4), 7);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(1, 6), 8);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(2, 1), 9);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(2, 3), 10);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(2, 5), 11);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(2, 7), 12);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(3, 0), 13);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(3, 2), 14);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(3, 4), 15);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(3, 6), 16);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(4, 1), 17);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(4, 3), 18);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(4, 5), 19);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(4, 7), 20);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(5, 0), 21);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(5, 2), 22);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(5, 4), 23);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(5, 6), 24);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(6, 1), 25);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(6, 3), 26);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(6, 5), 27);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(6, 7), 28);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(7, 0), 29);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(7, 2), 30);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(7, 4), 31);
        ROW_AND_COLUMN_TO_INDEX.put(new RowAndColumn(7, 6), 32);
    }
    
    public static int getLocationFor(RowAndColumn rc) {
        return ROW_AND_COLUMN_TO_INDEX.get(rc);
    }
    
    public static RowAndColumn getLocationFor(int index) {
        return INDEX_TO_ROW_AND_COLUMN.get(index);
    }
    
    public static boolean isValidPosition(int row, int column) {
    	return row >= 0 && row < GameState.WIDTH
    			&& column >= 0 && column < GameState.WIDTH
    			&& (row + column) % 2 == 1;
    }
}

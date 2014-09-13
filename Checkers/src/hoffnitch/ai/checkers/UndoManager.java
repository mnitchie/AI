package hoffnitch.ai.checkers;

import java.util.Stack;

public class UndoManager {
	
	private Stack<GameState> undo;
	private Stack<GameState> redo;
	
	public UndoManager() {
		undo = new Stack<GameState>();
		redo = new Stack<GameState>();
	}
	
	public void addBoard(GameState board) {
		undo.push(new GameState(board));
		
		if (redo.size() > 0)
			redo.clear();
	}
	
	public GameState undo() {
		redo.push(undo.pop());
		return new GameState(undo.peek());
	}
	
	public GameState redo() {
		undo.push(redo.pop());
		return new GameState(undo.peek());
	}
	
	public boolean hasNextUndo() {
		return undo.size() > 1;
	}
	
	public boolean hasNextRedo() {
		return redo.size() > 0;
	}
}

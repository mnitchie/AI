package hoffnitch.ai.checkers;

import java.util.LinkedList;
import java.util.List;

public class TurnValidator
{
	private Turn turnBeingBuilt;
	private List<Turn> filteredTurns;
	private List<Turn> possibleTurns;
	
	public TurnValidator() {
		filteredTurns = new LinkedList<Turn>();
		possibleTurns = new LinkedList<Turn>();
	}
	
	public Turn getTurnBeingBuild() {
		return turnBeingBuilt;
	}
	
//	public void setPossibleTurns(List<Turn> validTurns) {
//		this.possibleTurns = validTurns;
//	}
	
	public List<Turn> getFilteredTurns() {
		return filteredTurns;
	}
	
	public void setTurnBeingBuilt(Turn turn) {
		turnBeingBuilt = turn;
	}
	
	public void addPositionToTurn(Position position) {
		turnBeingBuilt.addMove(position);
	}
	
	public boolean hasNextMove() {
		return numFilteredTurns() == 1 && !filteredTurns.get(0).hasNextMove();
	}
	
	public void filterTurns(Piece piece) {
		int pieceIndex = piece.getPosition().index;
		for (int i = filteredTurns.size() - 1; i >= 0; i--) {
			if (filteredTurns.get(i).piecePositionIndex != pieceIndex)
				filteredTurns.remove(i);
		}
	}
	
	public int numFilteredTurns() {
		return filteredTurns.size();
	}
	
	public void filterTurns(Position position) {
		for (int i = filteredTurns.size() - 1; i >= 0; i--) {
			Turn turn = filteredTurns.get(i);
			Position next = turn.nextMove();
			
			if (!(next.equals(position)))
				filteredTurns.remove(i);
		}
	}
	
	public void setTurns(List<Turn> validTurns) {
		possibleTurns.clear();
		filteredTurns.clear();
		if (validTurns != null) {
			for (Turn turn: validTurns) {
				turn.resetIterator();
				filteredTurns.add(turn);
				possibleTurns.add(turn);
			}
		}
	}
	
	public void reset() {
		filteredTurns.clear();
		for (Turn turn: possibleTurns) {
			turn.resetIterator();
			filteredTurns.add(turn);
		}
		turnBeingBuilt = null;
	}
}

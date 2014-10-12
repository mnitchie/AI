package hoffnitch.ai.checkers;

/**
 * Class to determine if a stalemate has occurred.
 * Stalemates are indicated by the same moves being repeated x number of times
 */
public class StalemateDetector
{
	private static final int TURNS_IN_REPETITION = 4;
	private Turn[] previousTurns;
	private int numHalfTurns;
	private int maxHalfTurns;
	private boolean stalemate;
	
	/**
	 * Constructor for StalemateDetector
	 * @param maxRepetitions Max number of repetitions per player allowed before stalemate is called.
	 */
	public StalemateDetector(int maxRepetitions) {
		this.maxHalfTurns = TURNS_IN_REPETITION * maxRepetitions;
		previousTurns = new Turn[TURNS_IN_REPETITION];
	}
	
	/**
	 * Add a turn. Call isStalemate() afterward to see if a stalemate has occurred
	 * @param newTurn Turn to add
	 */
	public void addTurn(Turn newTurn) {
		
		// if new turn is equal to the player's previous turn, increase the number of repetitions
		Turn oldestTurn = previousTurns[0];
		if (oldestTurn != null && newTurn.equals(oldestTurn)) {
			numHalfTurns++;
			if (numHalfTurns == maxHalfTurns) {
				stalemate = true;
			}
		}
		
		// else, set repetitions back to 0
		else {
			numHalfTurns = 0;
		}
		
		// shift previous turns and add new turn
		for (int i = 0; i < previousTurns.length - 1; i++) {
			previousTurns[i] = previousTurns[i + 1];
		}
		previousTurns[previousTurns.length - 1] = newTurn;
	}
	
	/**
	 * @return Returns true if a stalemate was detected
	 */
	public boolean isStalemate() {
		return stalemate;
	}
	
	/**
	 * Reset the detector
	 */
	public void reset() {
		for (int i = 0; i < previousTurns.length; i++) {
			previousTurns[i] = null;
		}
		stalemate = false;
		numHalfTurns = 0;
	}
}

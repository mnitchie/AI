package hoffnitch.ai.statistics;

public class KingAndPawnWeights
{
	private double king;
	private double pawn;
	
	public KingAndPawnWeights() {
		this(0, 0);
	}
	
	public KingAndPawnWeights(double kingWeight, double pawnWeight) {
		king = kingWeight;
		pawn = pawnWeight;
	}

	public KingAndPawnWeights(KingAndPawnWeights other) {
		king = other.king;
		pawn = other.pawn;
	}
	
	public KingAndPawnWeights(KingAndPawnWeights other, double randomAmt) {
		pawn = other.pawn + 2 * Math.random() * randomAmt - randomAmt;
		king = other.king + 2 * Math.random() * randomAmt - randomAmt;
	}
	
	public void randomize(double amt) {
		king += Math.random() * amt * 2 - amt;
		pawn += Math.random() * amt * 2 - amt;
	}
	
	public double getKing() {
		return king;
	}

	public void setKing(double king) {
		this.king = king;
	}

	public double getPawn() {
		return pawn;
	}

	public void setPawn(double pawn) {
		this.pawn = pawn;
	}
	
	public String toString() {
		return pawn + " " + king;
	}
}

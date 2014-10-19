package statistics;

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
	
	
}

package statistics;

public class WeightSet
{
	private double king;
	private KingAndPawnWeights alignment;
	private double defendBackRow;
	private double defendCorner;
	private double random;
	private KingAndPawnWeights statics;
	private KingAndPawnWeights[] rings;
	private double promotion;
	private double border;
	private double adjacentDiagonal;
	private double tunneling;
	
	public WeightSet() {
		
	}

	public double getKing() {
		return king;
	}

	public void setKing(double king) {
		this.king = king;
	}

	public KingAndPawnWeights getAligned() {
		return alignment;
	}

	public void setAligned(KingAndPawnWeights inline) {
		this.alignment = inline;
	}

	public double getDefendBackRow() {
		return defendBackRow;
	}

	public void setDefendBackRow(double defendBackRow) {
		this.defendBackRow = defendBackRow;
	}

	public double getDefendCorner() {
		return defendCorner;
	}

	public void setDefendCorner(double defendCorner) {
		this.defendCorner = defendCorner;
	}

	public double getRandom() {
		return random;
	}

	public void setRandom(double random) {
		this.random = random;
	}

	public KingAndPawnWeights getStatics() {
		return statics;
	}

	public void setStatics(KingAndPawnWeights statics) {
		this.statics = statics;
	}

	public KingAndPawnWeights[] getRings() {
		return rings;
	}

	public void setRings(KingAndPawnWeights[] rings) {
		this.rings = rings;
	}

	public double getPromotion() {
		return promotion;
	}

	public void setPromotion(double promotion) {
		this.promotion = promotion;
	}

	public double getBorder() {
		return border;
	}

	public void setBorder(double border) {
		this.border = border;
	}

	public double getAdjacentDiagonal() {
		return adjacentDiagonal;
	}

	public void setAdjacentDiagonal(double adjacentDiagonal) {
		this.adjacentDiagonal = adjacentDiagonal;
	}

	public double getTunneling() {
		return tunneling;
	}

	public void setTunneling(double tunneling) {
		this.tunneling = tunneling;
	}
	
}

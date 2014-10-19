package statistics;

public class WeightSet
{
	private double staticKing;
	private double defendBackRow;
	private double defendCorner;
	private double random;
	private double promotion;
	private double border;
	private double adjacentDiagonal;
	private double tunneling;
	private double pieceCount;
	private KingAndPawnWeights statics;
	private KingAndPawnWeights alignment;
	private KingAndPawnWeights[] rings;
	
	public WeightSet() {
		
	}

	public double getStaticKing() {
		return staticKing;
	}

	public void setStaticKing(double staticKing) {
		this.staticKing = staticKing;
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

	public double getPieceCount() {
		return pieceCount;
	}

	public void setPieceCount(double pieceCount) {
		this.pieceCount = pieceCount;
	}

	public KingAndPawnWeights getStatics() {
		return statics;
	}

	public void setStatics(KingAndPawnWeights statics) {
		this.statics = statics;
	}

	public KingAndPawnWeights getAlignment() {
		return alignment;
	}

	public void setAlignment(KingAndPawnWeights alignment) {
		this.alignment = alignment;
	}

	public KingAndPawnWeights[] getRings() {
		return rings;
	}

	public void setRings(KingAndPawnWeights[] rings) {
		this.rings = rings;
	}
	
}

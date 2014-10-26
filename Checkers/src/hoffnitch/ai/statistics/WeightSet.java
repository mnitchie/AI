package hoffnitch.ai.statistics;

import java.util.Scanner;

public class WeightSet
{
	private double defendBackRow;
	private double defendCorner;
	private double random;
	private double promotion;
	private double adjacentDiagonal;
	private double tunneling;
	private double pieceCount;
	private KingAndPawnWeights statics;
	private KingAndPawnWeights sides;
	private KingAndPawnWeights alignment;
	private KingAndPawnWeights[] rings;
	
	/*public WeightSet() {
		
		
		
		statics = new KingAndPawnWeights(80, 112);
		sides = new KingAndPawnWeights();
		alignment = new KingAndPawnWeights();
		rings = new KingAndPawnWeights[3];
		for (int i = 0; i < rings.length; i++) {
			rings[i] = new KingAndPawnWeights();
		}
	}*/

	public WeightSet(WeightSet base, double randomAmt) {
		defendBackRow = base.defendBackRow + 2 * Math.random() * randomAmt - randomAmt;
		defendCorner = base.defendCorner + 2 * Math.random() * randomAmt - randomAmt;
		random = base.random + 2 * Math.random() * randomAmt - randomAmt;
		promotion = base.promotion + 2 * Math.random() * randomAmt - randomAmt;
		adjacentDiagonal = base.adjacentDiagonal + 2 * Math.random() * randomAmt - randomAmt;
		tunneling = base.tunneling + 2 * Math.random() * randomAmt - randomAmt;
		pieceCount = base.pieceCount + 2 * Math.random() * randomAmt - randomAmt;
		sides = new KingAndPawnWeights(base.sides, randomAmt);
		statics = new KingAndPawnWeights(base.statics, randomAmt);
		alignment = new KingAndPawnWeights(base.alignment, randomAmt);
		rings = new KingAndPawnWeights[3];
		for (int i = 0; i < rings.length; i++) {
			rings[i] = new KingAndPawnWeights(base.rings[i], randomAmt);
		}
	}
	
	public WeightSet() {
		
		
		defendBackRow = 2.1706042853676992;
		defendCorner = 18.480780045470375;
		random = -2.3942541821310668;
		promotion = -10.48661071235395;
		adjacentDiagonal = 19.394006384187787;
		tunneling = 9.149229285199148;
		pieceCount = 4.130419758222964;
		
		statics = new KingAndPawnWeights(124.00917410333409, 99.73100152907689);
		sides = new KingAndPawnWeights(3.141560135961538, -3.1940545185050633);
		alignment = new KingAndPawnWeights(-19.298299094004733, -0.605725619038517);
		rings = new KingAndPawnWeights[3];
		rings[0] = new KingAndPawnWeights(11.14798979847594, 10.459792353293249);
		rings[1] = new KingAndPawnWeights(-20.387755107052488, 3.3895462727133783);
		rings[2] = new KingAndPawnWeights(-3.6850907228087735, 0.8997281993811628);

	}
	
	public WeightSet(String s) {
		// 13.68214483832713 -3.3593541227800894 -18.614011207101726 -8.427563087177857 24.733275938514286 4.2835843349729394 8.129723189583823 
		// 108.17919399457409 122.3562494551536 
		// 2.195074394060017 15.197925274939884 
		// 4.0071615713202 -18.876614405374248 
		// 21.994942261458263 29.966602152658815
		// -3.549389405982067 -11.00700260279995
		// -0.8853216694784707 -11.901819467677072
		Scanner scan = new Scanner(s);
		defendBackRow = scan.nextDouble();
		defendCorner = scan.nextDouble();
		random = scan.nextDouble();
		promotion = scan.nextDouble();
		adjacentDiagonal = scan.nextDouble();
		tunneling = scan.nextDouble();
		pieceCount = scan.nextDouble();

		statics = makeKingAndPawnWeight(scan);
		sides = makeKingAndPawnWeight(scan);
		alignment = makeKingAndPawnWeight(scan);
		rings = new KingAndPawnWeights[3];
		for (int i = 0 ; i < 3; i++) {
			rings[i] = makeKingAndPawnWeight(scan);
		}
		scan.close();
	}
	
	public static KingAndPawnWeights makeKingAndPawnWeight(Scanner scan) {
		double pawn = scan.nextDouble();
		double king = scan.nextDouble();
		return new KingAndPawnWeights(king, pawn);
	}
	
	public String toString() {
		return defendBackRow + " " 
				+ defendCorner + " "
				+ random + " "
				+ promotion + " "
				+ adjacentDiagonal + " "
				+ tunneling + " "
				+ pieceCount + " "
				+ statics.getPawn() + " "
				+ statics.getKing() + " "
				+ sides.getPawn() + " "
				+ sides.getKing() + " "
				+ alignment.getPawn() + " "
				+ alignment.getKing() + " "
				+ getRingStrings()
				;
	}
	
	public KingAndPawnWeights getStatics() {
		return statics;
	}

	public void setStatics(KingAndPawnWeights statics) {
		this.statics = statics;
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

	public KingAndPawnWeights getSides() {
		return sides;
	}

	public void setSides(KingAndPawnWeights sides) {
		this.sides = sides;
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
	
	public String getRingStrings() {
		String output = "";
		for (int i = 0; i < rings.length; i++) {
			output += rings[i].getPawn() + " "
					+ rings[i].getKing() + " "
					;
		}
		return output;
	}
}

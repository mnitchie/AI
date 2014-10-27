package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;

public class Tournament {
	
    public static void main(String[] args) throws Exception {
    	
    	PositionScorer bestAI = null;
    	BasicWeightSet bestWeights = new BasicWeightSet();
    	double previousBest = Double.NEGATIVE_INFINITY;
    	
        double changeAmt = 10;
        
        try {
	    	while(changeAmt > 1) {
	    		BasicWeightSet weights = new BasicWeightSet(bestWeights);
			
	    		adjustWeights(weights, changeAmt);
	    		System.out.printf("change amt: %.4f\n", changeAmt);
	    		System.out.print(weights.defenseWeight + " ");
	    		System.out.print(weights.promotionWeight + " ");
	    		System.out.print(weights.centeredWeight + " ");
	    		System.out.print(weights.kingAlignmentWeight + " ");
	    		System.out.print(weights.pieceCountWeight + " ");
	    		System.out.print(weights.randomWeight + " ");
	    		System.out.print(weights.staticWeight + " ");
	    		System.out.println();
	    		
	    		// run for dark
	    		PositionScorer tyler = makeTyler(weights, PieceColor.DARK);
	    		CountBot mike = new CountBot(PieceColor.LIGHT, 1.4);
	    		AutomatedGameSession game = new AutomatedGameSession(tyler, mike, 3);
	    		double sessionScore = game.play(PieceColor.DARK);
	
	    		// run for light
	    		tyler = makeTyler(weights, PieceColor.LIGHT);
	    		mike = new CountBot(PieceColor.DARK, 1.4);
	    		game = new AutomatedGameSession(mike, tyler, 3);
	    		sessionScore += game.play(PieceColor.LIGHT);
	    		
	    		System.out.println("score: " + sessionScore + " vs " + previousBest);
	    		if (sessionScore > previousBest) {
	    			previousBest = sessionScore;
	    			bestAI = tyler;
	    			bestWeights = weights;
	    			System.out.println(		"-----------\n"
	    								+ 	" Best yet! \n"
	    								+ 	"-----------\n");
	    		}
	    		
	    		changeAmt *= 0.995;
	    	}
        }
        catch (OutOfMemoryError e) {
        	System.out.println(e.getMessage());
        }
    	System.out.println("done");
    	System.out.println("BEST:");
    	
    	System.out.printf("change amt: %.4f\n", changeAmt);
		System.out.print(bestWeights.defenseWeight + " ");
		System.out.print(bestWeights.promotionWeight + " ");
		System.out.print(bestWeights.centeredWeight + " ");
		System.out.print(bestWeights.kingAlignmentWeight + " ");
		System.out.print(bestWeights.pieceCountWeight + " ");
		System.out.print(bestWeights.randomWeight + " ");
		System.out.print(bestWeights.staticWeight + " ");
    	
    }
    
    public static void adjustWeights(BasicWeightSet weights, double changeAmt) {
    	weights.defenseWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.promotionWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.centeredWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.kingAlignmentWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.pieceCountWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.randomWeight += Math.random() * changeAmt * 2 - changeAmt;
    	weights.staticWeight += Math.random() * changeAmt * 2 - changeAmt;
    }
    
    public static PositionScorer makeTyler(BasicWeightSet weights, PieceColor color) {
    	PositionScorer output = new PositionScorer(color);
    	
    	output.setDefenseWeight(weights.defenseWeight);
    	output.setPromotionWeight(weights.promotionWeight);
    	output.setCenteredWeight(weights.centeredWeight);
    	output.setKingAlignmentWeight(weights.kingAlignmentWeight);
    	output.setPieceCountWeight(weights.pieceCountWeight);
    	output.setRandomWeight(weights.randomWeight);
    	output.setStaticWeight(weights.staticWeight);
    	
    	return output;
    }
    
    private static class BasicWeightSet {
    	double defenseWeight = 4;
    	double promotionWeight = 4;
    	double centeredWeight = 4;
    	double kingAlignmentWeight = 0.0;
    	double pieceCountWeight = 0.0;
    	double randomWeight = 0.0;
    	double staticWeight = 80;
    	
    	public BasicWeightSet() {
    		
    	}
    	
    	public BasicWeightSet(BasicWeightSet copy) {
    		defenseWeight = copy.defenseWeight;
    		promotionWeight = copy.promotionWeight;
    		centeredWeight = copy.centeredWeight;
    		kingAlignmentWeight = copy.kingAlignmentWeight;
    		pieceCountWeight = copy.pieceCountWeight;
    		randomWeight = copy.randomWeight;
    		staticWeight = copy.staticWeight;
    	}
    }
}

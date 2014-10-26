package hoffnitch.ai.statistics;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.ai.AutomatedGameSession;
import hoffnitch.ai.checkers.ai.CountBot;
import hoffnitch.ai.checkers.ai.PositionScorer;
import hoffnitch.ai.checkers.ai.TylerBot;

public class TylerTournament
{
	private static final WeightSet bestStatic = new WeightSet("13.68214483832713 -3.3593541227800894 -18.614011207101726 -8.427563087177857 24.733275938514286 4.2835843349729394 8.129723189583823 108.17919399457409 122.3562494551536 2.195074394060017 15.197925274939884 4.0071615713202 -18.876614405374248 21.994942261458263 29.966602152658815 -3.549389405982067 -11.00700260279995 -0.8853216694784707 -11.901819467677072");
	public static void main(String[] args) throws Exception {
    	
    	TylerBot bestAI = null;
    	WeightSet bestWeights = bestStatic;
    	double previousBest = Double.NEGATIVE_INFINITY;
    	
        double changeAmt = 5;
        
        System.out.println(bestWeights);
        WeightSet weights = bestWeights;
        
        try {
	    	while(changeAmt > 1) {
			
	    		//adjustWeights(weights, changeAmt);
	    		System.out.printf("change amt: %.4f\n", changeAmt);
	    		System.out.print(weights);
	    		
	    		// run for dark
	    		TylerBot tyler = new TylerBot(PieceColor.DARK, weights);
	    		CountBot mike = new CountBot(PieceColor.LIGHT, 1.4);
	    		AutomatedGameSession game = new AutomatedGameSession(tyler, mike, 1);
	    		double sessionScore = game.play(PieceColor.DARK);
	    		
	    		// if that didn't suck, try as the other color
	    		if (sessionScore > 0) {
		    		// run for light
		    		tyler = new TylerBot(PieceColor.LIGHT, weights);
		    		mike = new CountBot(PieceColor.DARK, 1.4);
		    		game = new AutomatedGameSession(mike, tyler, 1);
		    		sessionScore += game.play(PieceColor.LIGHT);
	    		}
	    		System.out.println("score: " + sessionScore + " vs " + previousBest);
	    		if (sessionScore > previousBest) {
	    			previousBest = sessionScore;
	    			bestAI = tyler;
	    			bestWeights = weights;
	    			System.out.println(		"-----------\n"
	    								+ 	" Best yet! \n"
	    								+ 	"-----------\n");
	    		}
	    		
	    		changeAmt *= 0.998;
	    		weights = new WeightSet(bestWeights, changeAmt);
	    	}
        }
        catch (OutOfMemoryError e) {
        	System.out.println(e.getMessage());
        }
    	System.out.println("done");
    	System.out.println("BEST:");
    	
    	System.out.printf("change amt: %.4f\n", changeAmt);
//		System.out.print(bestWeights.defenseWeight + " ");
//		System.out.print(bestWeights.promotionWeight + " ");
//		System.out.print(bestWeights.centeredWeight + " ");
//		System.out.print(bestWeights.kingAlignmentWeight + " ");
//		System.out.print(bestWeights.pieceCountWeight + " ");
//		System.out.print(bestWeights.randomWeight + " ");
//		System.out.print(bestWeights.staticWeight + " ");
    	
    }
    
//    public static void adjustWeights(BasicWeightSet weights, double changeAmt) {
//    	weights.defenseWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.promotionWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.centeredWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.kingAlignmentWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.pieceCountWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.randomWeight += Math.random() * changeAmt * 2 - changeAmt;
//    	weights.staticWeight += Math.random() * changeAmt * 2 - changeAmt;
//    }
    
//    public static PositionScorer makeTyler(BasicWeightSet weights, PieceColor color) {
//    	PositionScorer output = new PositionScorer(color);
//    	
//    	output.setDefenseWeight(weights.defenseWeight);
//    	output.setPromotionWeight(weights.promotionWeight);
//    	output.setCenteredWeight(weights.centeredWeight);
//    	output.setKingAlignmentWeight(weights.kingAlignmentWeight);
//    	output.setPieceCountWeight(weights.pieceCountWeight);
//    	output.setRandomWeight(weights.randomWeight);
//    	output.setStaticWeight(weights.staticWeight);
//    	
//    	return output;
//    }
}

package hoffnitch.ai.checkers.experiments;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.ai.AutomatedGameSession;
import hoffnitch.ai.checkers.ai.CountBot;

public class WeightVariationExperiments {
    public static void main(String[] args) throws Exception {
        
        AutomatedGameSession random;
//        for (double i = 1; i <= 2.1; i += .1) {
            System.out.println("****");
            System.out.println("Control is black, experiment is red");
            System.out.println("king weight: " + 4);
            System.out.println("****");
            
            random = new AutomatedGameSession(new CountBot(
                    PieceColor.DARK, 1.4), new CountBot(PieceColor.LIGHT, 4), 1);
            random.play();
            
            System.out.println("****");
            System.out.println("Experiment is black, control is red");
            System.out.println("king weight: " + 4);
            System.out.println("****");
            random = new AutomatedGameSession(new CountBot(
                    PieceColor.DARK, 4), new CountBot(PieceColor.LIGHT, 1.4), 1);
            
            random.play();
//        }
    }
}

package hoffnitch.ai.checkers.experiments;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.ai.AutomatedGameSession;
import hoffnitch.ai.checkers.ai.CornerDefender;
import hoffnitch.ai.checkers.ai.RatioBot;

public class WeightVariationExperiments {
    public static void main(String[] args) throws Exception {
        
        AutomatedGameSession random;
        for (double i = 1; i <= 2; i += .1) {
            System.out.println("****");
            System.out.println("Control is black, experiment is red");
            System.out.println("king weight: " + i);
            System.out.println("****");
            random = new AutomatedGameSession(new RatioBot(
                    PieceColor.DARK, 1.4), new RatioBot(PieceColor.LIGHT, i), 1);
            
            System.out.println("****");
            System.out.println("Experiment is black, control is red");
            System.out.println("king weight: " + i);
            System.out.println("****");
            random = new AutomatedGameSession(new RatioBot(
                    PieceColor.DARK, i), new CornerDefender(PieceColor.LIGHT, 1.4), 1);
            
            random.play();
        }
    }
}

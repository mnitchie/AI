package hoffnitch.ai.checkers.experiments;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.ai.AutomatedGameSession;
import hoffnitch.ai.checkers.ai.CornerDefender;
import hoffnitch.ai.checkers.ai.RatioBot;

public class WeightVariationExperiments {
    public static void main(String[] args) throws Exception {
        
        for (int i = 1; i <= 10; i++) {
            System.out.println("****");
            System.out.println("Control is black, experiment is red");
            System.out.println("king weight: " + i);
            System.out.println("****");
            AutomatedGameSession random = new AutomatedGameSession(new RatioBot(
                    PieceColor.DARK), new CornerDefender(PieceColor.LIGHT), 1);
            
            System.out.println("****");
            System.out.println("Control is black, experiment is red");
            System.out.println("king weight: " + i);
            System.out.println("****");
            AutomatedGameSession random = new AutomatedGameSession(new RatioBot(
                    PieceColor.DARK), new CornerDefender(PieceColor.LIGHT), 1);
            
            random.play();
        }
    }
}

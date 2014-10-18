package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;

public class Tournament {
    public static void main(String[] args) throws Exception {
        AutomatedGameSession random = new AutomatedGameSession(new RatioBot(
                PieceColor.DARK, 1.4), new CornerDefender(PieceColor.LIGHT, 1.4), 6);
        
        random.play();
    }
}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;

public class Tournament {
    public static void main(String[] args) {
        AutomatedGameSession random = new AutomatedGameSession(new RandomBot(
                PieceColor.DARK), new RandomBot(PieceColor.LIGHT), 1000000);
        
        random.play();
    }
}

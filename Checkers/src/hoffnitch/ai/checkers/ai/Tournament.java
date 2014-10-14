package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;

public class Tournament {
    public static void main(String[] args) throws Exception {
//        DBConnection db = new DBConnection();
        AutomatedGameSession random = new AutomatedGameSession(new CornerDefender(
                PieceColor.DARK), new RatioBot(PieceColor.LIGHT), 6);
        
        random.play();
    }
}

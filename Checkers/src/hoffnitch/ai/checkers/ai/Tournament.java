package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;

public class Tournament {
    public static void main(String[] args) throws Exception {
//        DBConnection db = new DBConnection();
        AutomatedGameSession random = new AutomatedGameSession(new RatioBot(
                PieceColor.DARK), new RatioWithKingsBot(PieceColor.LIGHT), 1);
        
        random.play();
    }
}

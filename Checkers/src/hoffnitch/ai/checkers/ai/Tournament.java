package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.db.DBConnection;

public class Tournament {
    public static void main(String[] args) throws Exception {
//        DBConnection db = new DBConnection();
        AutomatedGameSession random = new AutomatedGameSession(new RatioBot(
                PieceColor.DARK), new RandomBot(PieceColor.LIGHT), 5);
        
        random.play();
    }
}

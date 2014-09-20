package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.db.DBConnection;

public class Tournament {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        AutomatedGameSession random = new AutomatedGameSession(new RandomBot(
                PieceColor.DARK), new RandomBot(PieceColor.LIGHT), 1);
        
        random.play();
    }
}

package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.List;

public enum CheckersScenario {
    ONE_JUMP {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.BLACK, new Position(18)));
            scenario.add(new Piece(PieceColor.WHITE, new Position(14)));
            
            return scenario;
        }
    }
    ;
    
    public abstract List<Piece> getScenario();
}

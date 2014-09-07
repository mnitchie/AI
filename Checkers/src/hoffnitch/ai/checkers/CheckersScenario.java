package hoffnitch.ai.checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum CheckersScenario {
    SINGLE_JUMP_ONE_OPTION {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.WHITE, new Position(18)));
            scenario.add(new Piece(PieceColor.BLACK, new Position(14)));
            
            return scenario;
        }
    },
    SINGLE_JUMP_TWO_OPTIONS {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.WHITE, new Position(18)));
            scenario.add(new Piece(PieceColor.BLACK, new Position(14)));
            scenario.add(new Piece(PieceColor.BLACK, new Position(15)));
            return scenario;
        }
    },
    DOUBLE_JUMP_ONE_OPTION {
        public List<Piece> getScenario() {
            // Two possibilities are given. One is null (weird) and the other has
            // the jumps in the wrong order: 18-2-9, should be 18-9-2.
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.WHITE, new Position(18)));
            scenario.add(new Piece(PieceColor.BLACK, new Position(14)));
            scenario.add(new Piece(PieceColor.BLACK, new Position(6)));
            return scenario;
        }
    },
    DOUBLE_JUMP_ONE_OPTION_TWO_OPTIONS {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            return scenario;
        }
    },
    DOUBLE_JUMP_TWO_OPTIONS_AMBIGUOUS { // for easy copy and pasting to create new scenarios
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            return scenario;
        }
    },
    TEMPLATE { // for easy copy and pasting to create new scenarios
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            return scenario;
        }
    }
    ;
    
    public abstract List<Piece> getScenario();
}

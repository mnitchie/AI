package hoffnitch.ai.checkers.boardSetup;

import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

import java.util.ArrayList;
import java.util.List;

public enum CheckersScenario {
    LONELY_PIECE {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            return scenario;
        }
    },
    LONELY_KING {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)).setCrowned(true));
            return scenario;
        }
    },
    KING_ONE_OPTION {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)).setCrowned(true));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            return scenario;
        }
    },
    KING_BACKWARDS_DOUBLE_JUMP {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(2)).setCrowned(true));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(7)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(15)));
            return scenario;
        }
    },
    SINGLE_JUMP_AND_ADJACENT {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK,Position.getPosition(14)));
            return scenario;
        }
    },
    SINGLE_JUMP_ONE_OPTION {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            
            return scenario;
        }
    },
    SINGLE_JUMP_TWO_OPTIONS {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(15)));
            return scenario;
        }
    },
    DOUBLE_JUMP_ONE_OPTION {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(6)));
            return scenario;
        }
    },
    DOUBLE_JUMP_ONE_OPTION_TWO_OPTIONS {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(15)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(7)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(8)));

            return scenario;
        }
    },
    DOUBLE_JUMP_TWO_OPTIONS_AMBIGUOUS { // for easy copy and pasting to create new scenarios
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(15)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(6)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(7)));
            return scenario;
        }
    },
    DOUBLE_JUMP_TWO_OPTIONS_AMBIGUOUS_TWO_OPTIONS {
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(15)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(6)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(7)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(8)));
            return scenario;
        }
    },
    TWO_PIECES_ONE_JUMP_ONE_ADJACENT { // for easy copy and pasting to create new scenarios
        public List<Piece> getScenario() {
            List<Piece> scenario = new ArrayList<Piece>();
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(18)));
            scenario.add(new Piece(PieceColor.DARK, Position.getPosition(14)));
            
            scenario.add(new Piece(PieceColor.LIGHT, Position.getPosition(7)));
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

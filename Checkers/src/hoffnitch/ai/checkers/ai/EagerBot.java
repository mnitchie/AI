package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.PlayerType;
import hoffnitch.ai.checkers.Turn;

import java.util.List;

public class EagerBot extends AIPlayer {
    private static final String HEURISTIC_DESCRIPTION = "Eager Bot";

    public EagerBot(PieceColor color) {
        super(HEURISTIC_DESCRIPTION, color);
    }

    @Override
    public Turn getTurn(List<Turn> options) {
        return options.get(0);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.AI;
    }

    @Override
    public int getVersion() {
        return 1;
    }
}

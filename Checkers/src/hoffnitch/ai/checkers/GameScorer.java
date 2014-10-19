package hoffnitch.ai.checkers;

public class GameScorer
{
	private static final double WIN_VALUE = 100;
	public static double score(PieceColor perspective, GameState board, PieceColor winner) {
		double score = 0;
		int numPlayerPieces = board.getPieces(perspective).size();
		int numOpponentPieces = board.getPieces(PieceColor.opposite(perspective)).size();
		score = numPlayerPieces - numOpponentPieces;
		if (winner != null) {
			if (perspective == winner) {
				score += WIN_VALUE;
			} else {
				score -= WIN_VALUE;
			}
		}
		return score;
	}
}

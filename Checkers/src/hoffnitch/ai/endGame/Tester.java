package hoffnitch.ai.endGame;

import java.util.HashMap;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

public class Tester
{
	private static final Position DEFAULT_POSITION = Position.getPosition(0);
	
	private static HashMap<Long, Integer> pathLengths;
	private static long pieceCounts;
	private static int turn;
	
	public static void main(String[] args) {
		System.out.println("running..");
		runIteration(1, 0, 1, 0, CondensedGameState.DARK);
		System.out.println("done");
	}
	
	public static void runIteration(int numBlackPawns, int numBlackKings, int numWhitePawns, int numWhiteKings, int turn) {
		Piece[] blackPawns = makePieces(numBlackPawns, PieceColor.DARK, false);
		Piece[] blackKings = makePieces(numBlackKings, PieceColor.DARK, true);
		Piece[] lightPawns = makePieces(numWhitePawns, PieceColor.LIGHT, false);
		Piece[] lightKings = makePieces(numWhiteKings, PieceColor.LIGHT, true);
		
		pathLengths = new HashMap<Long, Integer>();
		Tester.turn = turn;
		Tester.pieceCounts = CondensedGameState.condenseNumberPieces(numBlackPawns, numBlackKings, numWhitePawns, numWhiteKings, turn);
		testPieces(blackPawns, blackKings, lightPawns, lightKings);
	}
	
	private static Piece[] makePieces(int num, PieceColor color, boolean isCrowned) {
		Piece[] pieces = new Piece[num];
		for (int i = 0; i < num; i++) {
			pieces[i] = new Piece(color, DEFAULT_POSITION);
			pieces[i].setCrowned(isCrowned);
		}
		return pieces;
	}
	
	private static void testPieces(Piece[] blackPawns, Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings) {
		GameState board = new GameState();
		board.clear();
		
		setBlackPawns(blackPawns, blackKings, whitePawns, whiteKings, 0, 1, board, 1);
	}
	
	private static void setBlackPawns(Piece[] blackPawns, Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board, int iteration) {
		if (numPlaced == blackPawns.length) {
			setBlackKings(blackKings, whitePawns, whiteKings, 0, 1, board, iteration);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					blackPawns[numPlaced].setPosition(position);
					board.setPiece(blackPawns[numPlaced], position);
					setBlackPawns(blackPawns, blackKings, whitePawns, whiteKings, numPlaced + 1, i + 1, board, iteration);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private static void setBlackKings(Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board, int iteration) {
		if (numPlaced == blackKings.length) {
			setWhitePawns(whitePawns, whiteKings, 0, 1, board, iteration);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					blackKings[numPlaced].setPosition(position);
					board.setPiece(blackKings[numPlaced], position);
					setBlackKings(blackKings, whitePawns, whiteKings, numPlaced + 1, i + 1, board, iteration);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private static void setWhitePawns(Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board, int iteration) {
		if (numPlaced == whitePawns.length) {
			setWhiteKings(whiteKings, 0, 1, board, iteration);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					whitePawns[numPlaced].setPosition(position);
					board.setPiece(whitePawns[numPlaced], position);
					setWhitePawns(whitePawns, whiteKings, numPlaced + 1, i + 1, board, iteration);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private static void setWhiteKings(Piece[] whiteKings, int numPlaced, int firstIndex, GameState board, int iteration) {
		if (numPlaced == whiteKings.length) {
			setPathLength(board, iteration);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					whiteKings[numPlaced].setPosition(position);
					board.setPiece(whiteKings[numPlaced], position);
					setWhiteKings(whiteKings, numPlaced + 1, i + 1, board, iteration);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private static void setPathLength(GameState board, int iteration) {
		CondensedGameState condensed = new CondensedGameState(board, turn);
		Integer previousPathLength = pathLengths.get(condensed.indices);
		
		/*
		 * Only go further if no previous path has been found,
		 * or if the new path length might be less
		 */
		if (previousPathLength == null || iteration < previousPathLength) {
			
		}
	}
}

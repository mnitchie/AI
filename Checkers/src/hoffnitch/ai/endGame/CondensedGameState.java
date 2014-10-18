package hoffnitch.ai.endGame;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents GameStates w/ <= 12 pieces as 2 longs.
 * Why do this? I don't know..
 */
public class CondensedGameState
{
	public static final int DARK	= 0;
	public static final int LIGHT	= 1;
	
	private static final int COUNT_SHIFT = 4;
	private static final int INDEX_SHIFT = 5;
	private static final int COUNT_MASK = (int)Math.pow(2, COUNT_SHIFT) - 1;
	private static final int INDEX_MASK = (int)Math.pow(2, INDEX_SHIFT) - 1;

	public final long pieceCounts;
	public final long indices;
	
	public static void main(String[] args) {
		int dark[] = {3, 6, 7};
		int light[] = {1, 2, 14, 16};
		
		long indexes = condenseIndices(dark, light);
		Piece[] darkPawns = new Piece[2];
		Piece[] darkKings= new Piece[1];
		Piece[] lightPawns = new Piece[2];
		Piece[] lightKings = new Piece[2];
		
		fillPieces(indexes, darkPawns, darkKings, lightPawns, lightKings);
		
		GameState board = new GameState();
		board.clear();
		addPieces(board, darkPawns);
		addPieces(board, darkKings);
		addPieces(board, lightPawns);
		addPieces(board, lightKings);
		
		CondensedGameState test = new CondensedGameState(board, PieceColor.DARK);
		
		System.out.println(board);
		System.out.println(test.generateGameState());
	}
	
	public CondensedGameState(GameState board, int pieceColor) {
		this(board, (pieceColor == DARK)? PieceColor.DARK: PieceColor.LIGHT);
	}
	
	public CondensedGameState(GameState board, PieceColor turn) {
		
		List<Piece> darkPieces = board.getPieces(PieceColor.DARK);
		List<Piece> lightPieces = board.getPieces(PieceColor.LIGHT);

		int[] darkIndices = new int[darkPieces.size()];
		int[] lightIndices = new int[lightPieces.size()];
		
		List<Piece> darkKings = removeKings(darkPieces);
		List<Piece> lightKings = removeKings(lightPieces);

		int pos = 0;
		for (int i = 0; i < darkPieces.size(); i++) {
			darkIndices[pos++] = darkPieces.get(i).getPosition().index;
		}
		for (int i = 0; i < darkKings.size(); i++) {
			darkIndices[pos++] = darkKings.get(i).getPosition().index;
		}
		
		pos = 0;
		for (int i = 0; i < lightPieces.size(); i++) {
			lightIndices[pos++] = lightPieces.get(i).getPosition().index;
		}
		for (int i = 0; i < lightKings.size(); i++) {
			lightIndices[pos++] = lightKings.get(i).getPosition().index;
		}
		
		int turnIndex = (turn == PieceColor.DARK)? DARK: LIGHT;
		
		pieceCounts = condenseNumberPieces(darkPieces.size(), darkKings.size(), lightPieces.size(), lightKings.size(), turnIndex);
		indices = condenseIndices(darkIndices, lightIndices);
	}
	
	public CondensedGameState(long pieceCounts, long indices) {
		this.pieceCounts = pieceCounts;
		this.indices = indices;
	}
	
	public GameState generateGameState() {
		GameState board = new GameState();
		board.clear();

		Piece[] darkPawns = new Piece[getNumDarkPawns(pieceCounts)];
		Piece[] darkKings = new Piece[getNumDarkKings(pieceCounts)];
		Piece[] lightPawns = new Piece[getNumLightPawns(pieceCounts)];
		Piece[] lightKings = new Piece[getNumLightKings(pieceCounts)];
		
		fillPieces(indices, darkPawns, darkKings, lightPawns, lightKings);

		addPieces(board, darkPawns);
		addPieces(board, darkKings);
		addPieces(board, lightPawns);
		addPieces(board, lightKings);
		
		return board;
	}
	
	private static void addPieces(GameState board, Piece[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			board.setPiece(pieces[i], pieces[i].getPosition());
		}
	}
	
	public static List<Piece> removeKings(List<Piece> pieces) {
		List<Piece> kings = new ArrayList<Piece>();
		for (int i = pieces.size() - 1; i >= 0; i--) {
			if (pieces.get(i).isCrowned()) {
				kings.add(kings.size(), pieces.remove(i));
			}
		}
		return kings;
	}
	
	public static long condenseIndices(int[] darkIndices, int[] lightIndices) {
		long binary = 0;
		
		// dark
		for (int i = 0; i < darkIndices.length; i++) {
			binary = binary << INDEX_SHIFT;
			binary |= darkIndices[i] - 1;
		}
		
		// light
		for (int i = 0; i < lightIndices.length; i++) {
			binary = binary << INDEX_SHIFT;
			binary |= lightIndices[i] - 1;
		}
		
		return binary;
	}
	
	public static void fillPieces(long binary, Piece[] darkPawns, Piece[] darkKings, Piece[] lightPawns, Piece[] lightKings) {
		
		// light kings
		for (int i = lightKings.length - 1; i >= 0; i--) {
			int index = (int)binary & INDEX_MASK;
			lightKings[i] = new Piece(PieceColor.LIGHT, Position.getPosition(index + 1));
			lightKings[i].setCrowned(true);
			binary = binary >> INDEX_SHIFT;
		}

		// light pawns
		for (int i = lightPawns.length - 1; i >= 0; i--) {
			int index = (int)binary & INDEX_MASK;
			lightPawns[i] = new Piece(PieceColor.LIGHT, Position.getPosition(index + 1));
			binary = binary >> INDEX_SHIFT;
		}
		
		// dark kings
		for (int i = darkKings.length - 1; i >= 0; i--) {
			int index = (int)binary & INDEX_MASK;
			darkKings[i] = new Piece(PieceColor.DARK, Position.getPosition(index + 1));
			darkKings[i].setCrowned(true);
			binary = binary >> INDEX_SHIFT;
		}
		
		// dark pawns
		for (int i = darkPawns.length - 1; i >= 0; i--) {
			int index = (int)binary & INDEX_MASK;
			darkPawns[i] = new Piece(PieceColor.DARK, Position.getPosition(index + 1));
			binary = binary >> INDEX_SHIFT;
		}
	}
	
	public static long condenseNumberPieces(int numDarkPawn, int numDarkKing, int numLightPawn, int numLightKing, int turn) {
		return turn << (4 * COUNT_SHIFT) 
				| numDarkPawn << (3 * COUNT_SHIFT) 
				| numDarkKing<< (2 * COUNT_SHIFT) 
				| numLightPawn << (1 * COUNT_SHIFT) 
				| numLightKing;
	}
	
	public static int getTurn(long pieceCounts) {
		return (int)(pieceCounts >> (4 * COUNT_SHIFT)) & 1;
	}
	
	public static int getNumDarkPawns(long pieceCounts) {
		return (int)(pieceCounts >> (3 * COUNT_SHIFT)) & COUNT_MASK;
	}
	
	public static int getNumDarkKings(long pieceCounts) {
		return (int)(pieceCounts >> (2 * COUNT_SHIFT)) & COUNT_MASK;
	}
	
	public static int getNumLightPawns(long pieceCounts) {
		return (int)(pieceCounts >> (1 * COUNT_SHIFT)) & COUNT_MASK;
	}
	
	public static int getNumLightKings(long pieceCounts) {
		return (int)(pieceCounts) & COUNT_MASK;
	}
	
}

package hoffnitch.ai.endGame;

import hoffnitch.ai.checkers.CheckersTurnMoveGenerator;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.Turn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EndGameDatabaseBuilder
{
	private static final Position DEFAULT_POSITION = Position.getPosition(0);
	private static final int FIRST_WHITE_PAWN_INDEX = 5;
	private static final int LAST_BLACK_PAWN_INDEX = 28;
	
	public HashMap<Long, HashMap<Long, Integer>> endGameScenarios;
	private HashMap<Long, Integer> pathLengthsForCurrentPieceCount;
	private static List<Long> pieceCountsToAdd;
	private long pieceCounts;
	private PieceColor botColor;
	private PieceColor opponentColor;
	private int turn;
	private CheckersTurnMoveGenerator turnGenerator = new CheckersTurnMoveGenerator();
	
	public static void main(String[] args) {
		
		EndGameDatabaseBuilder tester = new EndGameDatabaseBuilder();
		System.out.println("running..");
		
		long beginTime = System.currentTimeMillis();
		final int MAX_PIECES = 3;
		final int MAX_LENGTH = 25;
		
		int longestPath = runTestsForColor(tester, MAX_PIECES, MAX_LENGTH, PieceColor.DARK);
		
		long runTime = System.currentTimeMillis() - beginTime;
		
		System.out.println("finished in " + runTime / 1000 + " seconds");
		System.out.println("longest path: " + longestPath);
		System.out.println("wins indexed: " + tester.getNumIndexedWins());
		
		System.out.println("writing to db..");
		saveToDatabase(tester.endGameScenarios);
		
		// This part is stupid testing
		long pC = CondensedGameState.condenseNumberPieces(0, 1, 0, 1, CondensedGameState.DARK);
		HashMap<Long, Integer> m = tester.endGameScenarios.get(pC);
		Iterator<Long> iterator = m.keySet().iterator();
		
		int shown = 0;
		while (shown < 2) {
			Long in = iterator.next();
			CondensedGameState cgs = new CondensedGameState(pC, in);
			int len = m.get(in);
			if (len == 1) {
				getDistanceFromDatabase(cgs.pieceCounts, cgs.indices);
				System.out.println(len);
				System.out.println(cgs.generateGameState());
				shown++;
			}
		}
		// end stupid part
	}
	
	private static void saveToDatabase(HashMap<Long, HashMap<Long, Integer>> endGameScenarios) {
		Connection connection = EndGameDatabaseManager.getConnection();
		EndGameDatabaseManager.createTable(connection);
		EndGameDatabaseManager.batchInsertGameStates(connection, endGameScenarios, pieceCountsToAdd);
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void getDistanceFromDatabase(long pieceCount, long indices) {
		Connection connection = EndGameDatabaseManager.getConnection();
		
		int distance = EndGameDatabaseManager.selectGameState(connection, pieceCount, indices);
		System.out.println("DIST FROM DB: " + distance);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int runTestsForColor(EndGameDatabaseBuilder tester, int maxPieces, int maxLength, PieceColor color) {
		// will this work??
		int longestPath = 0;
		
		final int MIN_PIECE_COUNT = 2;
		final int MAX_BLACK_PAWNS = 1;
		
		for (int numPieces = MIN_PIECE_COUNT; numPieces <= maxPieces; numPieces++) {
			for (int numBlack = 1; numBlack < numPieces; numBlack++) {
				int numWhite = numPieces - numBlack;
				for (int numBlackPawns = 0; numBlackPawns <= MAX_BLACK_PAWNS && numBlackPawns <= numBlack; numBlackPawns++) {
					int numBlackKings = numBlack - numBlackPawns;
					for (int numWhitePawns = 0; numWhitePawns <= numWhite; numWhitePawns++) {
						int numWhiteKings = numWhite - numWhitePawns;
						long pieceCount = CondensedGameState.condenseNumberPieces(numBlackPawns, numBlackKings, numWhitePawns, numWhiteKings, CondensedGameState.DARK);
						if (!EndGameDatabaseManager.hasPieceCount(pieceCount)) {
							pieceCountsToAdd.add(pieceCount);
							int newLongestPath = tester.runRange(numBlackPawns, numBlackKings, numWhitePawns, numWhiteKings, color, 1, maxLength);
							if (newLongestPath > longestPath) {
								longestPath = newLongestPath;
							}
						}
					}
				}
			}
		}
		return longestPath;
	}
	
	public EndGameDatabaseBuilder() {
		EndGameData databaseData = new EndGameData();
		
		// block until data is loaded
		System.out.println("loading..");
		while (!databaseData.isLoaded());
		System.out.println("done");
		
		endGameScenarios = new HashMap<Long, HashMap<Long, Integer>>();
		pieceCountsToAdd = new LinkedList<Long>();
	}
	
	public long getNumIndexedWins() {
		long winCount = 0;
		
		Iterator<HashMap<Long, Integer>> maps = endGameScenarios.values().iterator();
		while (maps.hasNext()) {
			winCount += maps.next().size(); 
		}
		
		return winCount;
	}
	
	
	public int runRange(int numBlackPawns, int numBlackKings, int numWhitePawns, int numWhiteKings, PieceColor playerColor, int minTurns, int maxTurns) {
		
		Piece[] blackPawns = makePieces(numBlackPawns, PieceColor.DARK, false);
		Piece[] blackKings = makePieces(numBlackKings, PieceColor.DARK, true);
		Piece[] lightPawns = makePieces(numWhitePawns, PieceColor.LIGHT, false);
		Piece[] lightKings = makePieces(numWhiteKings, PieceColor.LIGHT, true);
		
		turn = (playerColor == PieceColor.DARK)? CondensedGameState.DARK: CondensedGameState.LIGHT;
		botColor = playerColor;
		opponentColor = PieceColor.opposite(playerColor);
		pieceCounts = CondensedGameState.condenseNumberPieces(numBlackPawns, numBlackKings, numWhitePawns, numWhiteKings, turn);
		
		// make new hashmap for the current pieceCount
		pathLengthsForCurrentPieceCount = new HashMap<Long, Integer>();
		endGameScenarios.put(pieceCounts, pathLengthsForCurrentPieceCount);
		
		int longestPath = 0;
		int mapSize = pathLengthsForCurrentPieceCount.size();
		for (int i = minTurns; i < maxTurns; i++) {
			testPieces(blackPawns, blackKings, lightPawns, lightKings);
			int newMapSize = pathLengthsForCurrentPieceCount.size();
			
			System.out.println("solved iteration " + i + " w/ " + numBlackKings + " dark kings, " + numBlackPawns + " dark pawns, " + numWhiteKings + " white kings, and " + numWhitePawns + " white pawns");
			
			if (newMapSize > mapSize) {
				longestPath = i; 
				mapSize = newMapSize;
			} else {
				break;
			}
		}
		
		return longestPath;
	}
	
	private Piece[] makePieces(int num, PieceColor color, boolean isCrowned) {
		Piece[] pieces = new Piece[num];
		for (int i = 0; i < num; i++) {
			pieces[i] = new Piece(color, DEFAULT_POSITION);
			pieces[i].setCrowned(isCrowned);
		}
		return pieces;
	}
	
	private void testPieces(Piece[] blackPawns, Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings) {
		GameState board = new GameState();
		board.clear();
		
		setBlackPawns(blackPawns, blackKings, whitePawns, whiteKings, 0, 1, board);
	}
	
	private void setBlackPawns(Piece[] blackPawns, Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board) {
		if (numPlaced == blackPawns.length) {
			setBlackKings(blackKings, whitePawns, whiteKings, 0, 1, board);
		}
		
		else {
			for (int i = firstIndex; i <= LAST_BLACK_PAWN_INDEX; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					blackPawns[numPlaced].setPosition(position);
					board.setPiece(blackPawns[numPlaced], position);
					setBlackPawns(blackPawns, blackKings, whitePawns, whiteKings, numPlaced + 1, i + 1, board);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private void setBlackKings(Piece[] blackKings, Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board) {
		if (numPlaced == blackKings.length) {
			setWhitePawns(whitePawns, whiteKings, 0, FIRST_WHITE_PAWN_INDEX, board);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					blackKings[numPlaced].setPosition(position);
					board.setPiece(blackKings[numPlaced], position);
					setBlackKings(blackKings, whitePawns, whiteKings, numPlaced + 1, i + 1, board);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private void setWhitePawns(Piece[] whitePawns, Piece[] whiteKings, int numPlaced, int firstIndex, GameState board) {
		if (numPlaced == whitePawns.length) {
			setWhiteKings(whiteKings, 0, 1, board);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					whitePawns[numPlaced].setPosition(position);
					board.setPiece(whitePawns[numPlaced], position);
					setWhitePawns(whitePawns, whiteKings, numPlaced + 1, i + 1, board);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private void setWhiteKings(Piece[] whiteKings, int numPlaced, int firstIndex, GameState board) {
		if (numPlaced == whiteKings.length) {
			setPathLength(board);
		}
		
		else {
			for (int i = firstIndex; i <= 32; i++) {
				if (board.getPieceAtPosition(i) == null) {
					Position position = Position.getPosition(i);
					whiteKings[numPlaced].setPosition(position);
					board.setPiece(whiteKings[numPlaced], position);
					setWhiteKings(whiteKings, numPlaced + 1, i + 1, board);
					board.setPiece(null, Position.getPosition(i));
				}
			}
		}
	}
	
	private void setPathLength(GameState board) {
		CondensedGameState condensed = new CondensedGameState(board, turn);
		Integer previousPathLength = pathLengthsForCurrentPieceCount.get(condensed.indices);
		
		// Only go further if no previous path has been found for the given indices
		if (previousPathLength == null) {
			List<Turn> opponentTurns = turnGenerator.getMovesForTurn(opponentColor, board);
			boolean winnable = true;
			int worstBestLength = 0;
			for (Turn opponentTurn: opponentTurns) {
				int bestCase = Integer.MAX_VALUE;
				GameState newBoard = new GameState(board);
				newBoard.doTurn(opponentTurn);
				List<Turn> playerTurns = turnGenerator.getMovesForTurn(botColor, newBoard);
				for (Turn playerTurn: playerTurns) {
					GameState finalBoard = new GameState(newBoard);
					finalBoard.doTurn(playerTurn);
					if (finalBoard.getPieces(opponentColor).size() == 0) {
						bestCase = 1;
						break;
					}
					else {
						CondensedGameState condensedFinalBoard = new CondensedGameState(finalBoard, botColor);
						HashMap<Long, Integer> mapForPieceCount = endGameScenarios.get(condensedFinalBoard.pieceCounts);
						
						if (mapForPieceCount == null) {
							mapForPieceCount = new HashMap<Long, Integer>();
							endGameScenarios.put(condensedFinalBoard.pieceCounts, mapForPieceCount);
						}
						
						Integer newPathLength = mapForPieceCount.get(condensedFinalBoard.indices);
						if (newPathLength != null && newPathLength < bestCase) {
							bestCase = newPathLength + 1;
						}
					}
				}
				if (bestCase == Integer.MAX_VALUE) {
					winnable = false;
					break;
				} else if (bestCase > worstBestLength) {
					worstBestLength = bestCase;
				}
			}
			if (winnable) {
				pathLengthsForCurrentPieceCount.put(condensed.indices, worstBestLength);
			}
		}
	}
}

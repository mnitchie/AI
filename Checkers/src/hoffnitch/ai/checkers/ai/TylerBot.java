package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.Direction;
import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.endGame.CondensedGameState;
import hoffnitch.ai.endGame.EndGameDatabaseManager;
import hoffnitch.ai.statistics.KingAndPawnWeights;
import hoffnitch.ai.statistics.WeightSet;

import java.security.AllPermission;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;


public class TylerBot extends AIPlayer
{
	public static final String HEURISTIC_DESCRIPTION = "TYLER.BOT";

	private static final int DARK_PAWN = 0;
	private static final int DARK_KING = 1;
	private static final int LIGHT_PAWN = 2;
	private static final int LIGHT_KING = 3;

	private WeightSet weights;
	private Connection endGameDatabaseConnection;
	private Statement databaseStatement;
	private PositionScores[] positionScores;
	private HashMap<Long, HashMap<Long, Integer>> endGameScenarios;
	
	public TylerBot(PieceColor color, WeightSet weights) {
		super(HEURISTIC_DESCRIPTION, color);
		this.weights = weights;
		generatePositionScores();
		endGameDatabaseConnection = EndGameDatabaseManager.getConnection();
		try {
			databaseStatement = endGameDatabaseConnection.createStatement();
			
			// load the entire database into a hashmap!!
			endGameScenarios = new HashMap<Long, HashMap<Long, Integer>>();
			
			System.out.println("querying for endgame data..");
			ResultSet allData = databaseStatement.executeQuery("SELECT * FROM endgame");
			System.out.println("got the data");
			System.out.println("loading endgame data to hashmap");
			
			int distance;
			long pieceCount;
			long indices;
			int numLoaded = 0;
			HashMap<Long, Integer> mapForPieceCount;
			while (allData.next()) {
				distance = allData.getInt("distance");
				pieceCount = allData.getLong("pieceCount");
				indices = allData.getLong("indices");
				
				mapForPieceCount = endGameScenarios.get(pieceCount);
				if (mapForPieceCount == null) {
					mapForPieceCount = new HashMap<Long, Integer>();
					endGameScenarios.put(pieceCount, mapForPieceCount);
				}
				
				mapForPieceCount.put(indices, distance);
				numLoaded++;
			}
			System.out.println("loaded");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getDistanceFromDatabase(GameState gameState) {
		final PieceColor EXPECTED_COLOR = PieceColor.DARK;
		
		if (getColor() != EXPECTED_COLOR) {
			gameState = gameState.getInverse();
		}
		
		CondensedGameState condensedGameState = new CondensedGameState(gameState, EXPECTED_COLOR);
		HashMap<Long, Integer> endGame = endGameScenarios.get(condensedGameState.pieceCounts);
		if (endGame != null) {
			Integer distanceToWin = endGame.get(condensedGameState.indices);
			
			if (distanceToWin != null) {
				return distanceToWin;
			}
		}
		
		return -1;
	}
	
	private void generatePositionScores() {
		this.positionScores = new PositionScores[32];
		for (int i = 0; i < positionScores.length; i++) {
			positionScores[i] = new PositionScores();
		}

		int index = 0;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row + col) % 2 == 1) {
					PositionScores scores = positionScores[index];
					//scoreForDefense(scores, row, col, defenseWeight);
					scoreDefendingCorner(scores, row, col, weights.getDefendCorner());
					scoreDefendingForBackRow(scores, row, col, weights.getDefendBackRow());
					scoreForPromotion(scores, row, weights.getPromotion());
					//scoreForCenteredness(positionScores[index], row, col, centeredWeight);
					scoreForSide(scores, col, weights.getSides());
					addStaticScore(positionScores[index], weights.getStatics());

					for (int i = 0; i < weights.getRings().length; i++) {
						KingAndPawnWeights ringWeight = weights.getRings()[i];
						scoreForRing(scores, row, col, i, ringWeight);
					}
					index++;
				}
			}
		}
	}

	private void addStaticScore(PositionScores scores, KingAndPawnWeights weights) {
		double pawnWeight = weights.getPawn();
		double kingWeight = weights.getKing();
		
		scores.alterBlackKingScore(kingWeight);
		scores.alterRedKingScore(kingWeight);
		scores.alterBlackPawnScore(pawnWeight);
		scores.alterRedPawnScore(pawnWeight);
	}

	/**
	 * Pawns should move forward
	 * 
	 * @param scores
	 * @param row
	 */
	private void scoreForPromotion(PositionScores scores, int row, double weight) {
		final int valPerRow = 1;
		final int maxRowVal = 7 * valPerRow;

		scores.alterBlackPawnScore(row * valPerRow * weight);
		scores.alterRedPawnScore((maxRowVal - (row * valPerRow)) * weight);
	}

	private void scoreForSide(PositionScores scores, int col,
			KingAndPawnWeights weights) {
		if (col == 0 || col == 7) {
			scores.alterBlackKingScore(weights.getKing());
			scores.alterRedKingScore(weights.getKing());
			scores.alterBlackPawnScore(weights.getPawn());
			scores.alterRedPawnScore(weights.getPawn());
		}
	}

	private void scoreForRing(PositionScores scores, int row, int col,
			int ring, KingAndPawnWeights weights) {
		if (row > 3) {
			row = 7 - row;
		}
		if (col > 3) {
			col = 7 - col;
		}
		int positionRing = Math.min(row, col);
		if (positionRing == ring) {
			scores.alterBlackKingScore(weights.getKing());
			scores.alterRedKingScore(weights.getKing());
			scores.alterBlackPawnScore(weights.getPawn());
			scores.alterRedPawnScore(weights.getPawn());
		}
	}

	private void scoreDefendingCorner(PositionScores scores, int row, int col,
			double weight) {
		double darkScore = 0;
		double lightScore = 0;

		if (row == 0 && col == 1) {
			darkScore += weight;
		} else if (row == 7 && col == 6) {
			lightScore += weight;
		}

		scores.alterBlackPawnScore(darkScore);
		scores.alterRedPawnScore(lightScore);

	}

	private void scoreDefendingForBackRow(PositionScores scores, int row,
			int col, double weight) {
		double darkScore = 0;
		double lightScore = 0;

		if (row == 0) {
			darkScore += weight;
		} else if (row == 7) {
			lightScore += weight;
		}

		scores.alterBlackPawnScore(darkScore);
		scores.alterRedPawnScore(lightScore);
	}

	@Override
	public Turn getTurn() {
		evaluateTurns();
		double turnScore = getTurnTree().getNextTurnValue();
		
		if (turnScore > 90000) {
			System.out.println("Winning in " + (int)(1000000 - turnScore) + " turns");
		}
		
		return getTurnTree().getBestTurn();
	}
	
	@Override
	public double evaluateBoard(GameState board) {
		
		if (board.getPieces(PieceColor.DARK).size() + board.getPieces(PieceColor.LIGHT).size() <= 5) {
			int distanceInDatabase = getDistanceFromDatabase(board);
			
			// if the board is in the database, then cool
			if (distanceInDatabase >= 0) {
				return 1000000 - distanceInDatabase;
			}
		}
		
		// otherwise, do the other stuff
		return scoreBoardOnPositions(board, positionScores)
				+ weights.getAdjacentDiagonal() * scoreOnDiagonalTeammate(board)
				+ weights.getAlignment().getPawn() * scoreBoardOnAlignedPawns(board)
				+ weights.getAlignment().getKing() * scoreBoardOnAlignedKings(board)
				+ weights.getPieceCount() * totalPieceCount(board)
				//+ weights.getRandom() * Math.random()
				+ weights.getTunneling() * scoreOnTunneling(board)
				;
	}

	private double totalPieceCount(GameState board) {
		return board.getPieces(PieceColor.DARK).size()
				+ board.getPieces(PieceColor.LIGHT).size();
	}

	private double scoreBoardOnAlignedPawns(GameState board) {
		double score = 0;

		int sideMultiplier = (getColor() == PieceColor.DARK) ? 1 : 0;

		List<Piece> playerPieces = board.getPieces(getColor());
		List<Piece> opponentPieces = board.getPieces(PieceColor
				.opposite(getColor()));

		for (Piece playerPiece : playerPieces) {
			if (!playerPiece.isCrowned()) {
				for (Piece opponentPiece : opponentPieces) {
					Position playerPosition = playerPiece.getPosition();
					Position opponentPosition = opponentPiece.getPosition();

					if (sideMultiplier * playerPosition.row < sideMultiplier
							* opponentPosition.row
							&& playerPosition.column == opponentPosition.column
							&& Math.abs(playerPosition.row
									- opponentPosition.row) == 2) {
						score += 1;
					}
				}
			}
		}
		return score;
	}

	private double scoreBoardOnAlignedKings(GameState board) {
		double score = 0;

		List<Piece> playerPieces = board.getPieces(getColor());
		List<Piece> opponentPieces = board.getPieces(PieceColor
				.opposite(getColor()));

		for (Piece playerPiece : playerPieces) {
			if (playerPiece.isCrowned()) {
				for (Piece opponentPiece : opponentPieces) {
					Position playerPosition = playerPiece.getPosition();
					Position opponentPosition = opponentPiece.getPosition();

					if (playerPosition.row == opponentPosition.row) {
						if (Math.abs(playerPosition.column
								- opponentPosition.column) == 2) {
							score += 1;
						}
					}

					else if (playerPosition.column == opponentPosition.column) {
						if (Math.abs(playerPosition.row - opponentPosition.row) == 2) {
							score += 1;
						}
					}
				}
			}
		}
		return score;
	}

	private double scoreOnDiagonalTeammate(GameState board) {
		return scoreOnDiagonalTeammate(board, getColor())
				- scoreOnDiagonalTeammate(board,
						PieceColor.opposite(getColor()));
	}

	private double scoreOnDiagonalTeammate(GameState board, PieceColor color) {
		double score = 0;
		List<Piece> pieces = board.getPieces(color);
		for (int i = 0; i < pieces.size(); i++) {
			Position positionA = pieces.get(i).getPosition();
			for (int j = 0; j < pieces.size(); j++) {
				Position positionB = pieces.get(j).getPosition();
				if (Math.abs(positionA.row - positionB.row) == 1
						&& Math.abs(positionA.column - positionB.column) == 1) {
					score++;
				}
			}
		}
		return score;
	}

	private double scoreOnTunneling(GameState board) {
		return scoreOnTunneling(board, getColor())
				- scoreOnTunneling(board, PieceColor.opposite(getColor()));
	}

	private double scoreOnTunneling(GameState board, PieceColor color) {
		double score = 0;
		List<Piece> playerPieces = board.getPieces(color);

		for (int i = 0; i < playerPieces.size(); i++) {
			Position position = playerPieces.get(i).getPosition();

			// test first direction
			Position topLeft = position.getOffsetPosition(Direction.TOP_LEFT);
			Position bottomRight = position
					.getOffsetPosition(Direction.BOTTOM_RIGHT);

			// i wish this line were longer...
			if ((topLeft == null || board.getPieceAtPosition(topLeft) != null
					&& (bottomRight == null) || board
						.getPieceAtPosition(bottomRight) != null)) {
				score++;
			}

			// test other direction
			Position topRight = position.getOffsetPosition(Direction.TOP_LEFT);
			Position bottomLeft = position
					.getOffsetPosition(Direction.BOTTOM_RIGHT);
			if ((topRight == null || board.getPieceAtPosition(topRight) != null
					&& (bottomLeft == null) || board
						.getPieceAtPosition(bottomLeft) != null)) {
				score++;
			}
		}
		return score;
	}

	public static void printPositionScores(PositionScores[] scores, int type) {
		int index = 0;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row + col) % 2 == 1) {
					PositionScores tile = scores[index];
					double value = 0;
					switch (type) {
					case DARK_KING:
						value = tile.getBlackKingScore();
						break;
					case DARK_PAWN:
						value = tile.getBlackPawnScore();
						break;
					case LIGHT_KING:
						value = tile.getRedKingScore();
						break;
					case LIGHT_PAWN:
						value = tile.getRedPawnScore();
						break;
					}
					System.out.printf(" %2.1f ", value);
					index++;
				} else {
					System.out.print("     ");
				}
			}
			System.out.println();
		}
	}

	public PositionScores[] getPositionScores() {
		return positionScores;
	}

	public void setPositionScores(PositionScores[] positionScores) {
		this.positionScores = positionScores;
	}

}

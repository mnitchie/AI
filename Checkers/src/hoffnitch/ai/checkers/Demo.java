package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.ai.AIPlayer;
import hoffnitch.ai.checkers.ai.PlayerFactory;
import hoffnitch.ai.checkers.ai.RemotePlayer;
import hoffnitch.ai.checkers.boardSetup.BoardInitializerFromFile;
import hoffnitch.ai.checkers.boardSetup.DefaultInitializer;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;
import hoffnitch.ai.checkers.gui.Arrow;
import hoffnitch.ai.checkers.gui.BoardCanvas;
import hoffnitch.ai.checkers.gui.CanvasView;
import hoffnitch.ai.checkers.gui.GuiPiece;
import hoffnitch.ai.checkers.gui.newGameMenu.NewGameMenu;
import hoffnitch.ai.checkers.gui.newGameMenu.PlayerInfo;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.server.RemoteCall;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputListener;

import checkersRemote.CheckersConnector;
import checkersRemote.RemotePlayerInfo;

public class Demo implements MouseInputListener, ActionListener
{
	private static final String NEW_GAME 		= "New game";
	private static final String LOBBY_ADDRESS 	= "127.0.0.1";
	private static final int LOBBY_PORT			= 5555;
	
	private static final int MAX_TREE_DEPTH		= 6;
	
	private GameState board;
	private CheckersTurnMoveGenerator moveGenerator;
	private CanvasView view;
	private Player white;
	private Player black;
	private Player currentPlayer;
	private PlayerFactory playerFactory;

	// Gui-related stuff
	private GuiPiece grabbedPiece;
	private Point grabOffset;
	private List<GuiPiece> guiPieces;
	private Map<Piece, GuiPiece> pieceMap;
	
	// animation stuff
	private int fps = 60;
	private TurnAnimator turnAnimator;
	
	// Turn-related stuff
	private Turn turnBeingBuilt;
	private List<Turn> filteredTurns;
	private List<Turn> possibleTurns;
	private boolean canMove;
	private UndoManager undoManager;
	
	public static void main(String[] args) {
		Demo demo = new Demo();
		//demo.start();
	}
	
	public Demo() {

		// attempt to set look and feel to match OS
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// no big deal if it throws an exception
		}
		
		playerFactory = new PlayerFactory();
		guiPieces = new LinkedList<GuiPiece>();
		pieceMap = new HashMap<Piece, GuiPiece>();
		filteredTurns = new LinkedList<Turn>();
		
		board = new GameState();
		moveGenerator = new CheckersTurnMoveGenerator();
		view = new CanvasView("Checkers", this);

		// default players
		black = new HumanPlayer("Tyler", PieceColor.DARK, view.canvas);
		white = new HumanPlayer("Mike", PieceColor.LIGHT, view.canvas);
		
		view.canvas.addMouseListener(this);
		view.canvas.addMouseMotionListener(this);
		
		turnAnimator = new TurnAnimator();
		
		view.setVisible(true);
		
		start();
	}
	
	private void giveTurn(Player player) {
		currentPlayer = player;
		if (isEliminated(player, board))
			endGame(getOpponent(player));
		
		else {
			List<Turn> validTurns = moveGenerator.getMovesForTurn(player.color, board);
			if (validTurns.size() == 0)
				endGame(getOpponent(player));
			else {
				canMove = false;
				grabbedPiece = null;
				
				// if human, enable moving pieces and wait for event to call doTurn
				if (player instanceof HumanPlayer) {
					possibleTurns = validTurns;
					setTurns();
					drawArrows(true);
					canMove = true;
				}
				
				// if remote player, get turn from them
				else if (player instanceof RemotePlayer) {
					RemotePlayer remotePlayer = (RemotePlayer)player;
					String action = remotePlayer.getOpponentAction();
					if (action.equals(CheckersConnector.MOVE))
						turnAnimator.animateTurn(remotePlayer.getTurn(board));
					else
						System.out.println("???");
				}
				
				// if ai, turn will be determined now
				else {
					//Turn turn = ((AIPlayer) player).getTurn(validTurns);
					AIPlayer ai = (AIPlayer)player;
					ai.evaluateTurns();
					Turn turn = ai.getTurn();
					turnAnimator.animateTurn(turn);
				}
			}
		}
	}
	
	private void endGame(Player winner) {
		drawArrows(false);
		System.out.println(winner.color.toString() + " wins");
	}
	
	private void doTurn(Turn turn) {
		view.textArea.append(turn.toString() + "\n");
		
		board.doTurn(turn);
		undoManager.addBoard(board);
		syncGuiWithGameState();
		
		Player opponent = getOpponent(currentPlayer);
		
		if (opponent instanceof RemotePlayer) {
			((RemotePlayer)opponent).sendTurn(turn);
		} else if (opponent instanceof AIPlayer) {
			try
			{
				((AIPlayer)opponent).getOpponentTurn(turn);
			} catch (InvalidTurnException e)
			{
				System.out.println("INVALID MOVE DETECTED");
				e.printStackTrace();
			}
		}
		
		giveTurn(opponent);
	}
	
	private Player getOpponent(Player player) {
		if (player == white)
			return black;
		else
			return white;
	}
	
	public void start() {
		start(black);
	}
	
	public void start(Player player) {
		initializePieces(board);
		undoManager = new UndoManager();
		undoManager.addBoard(board);
		giveTurn(player);
	}
	
	public void start(Player player1, Player player2) {
		
		if (player1.color == PieceColor.DARK) {
			black = player1;
			white = player2;
		}
		else {
			black = player2;
			white = player1;
		}
		
		start(black);
	}
	
	private void initializeAITree(AIPlayer player) {
		initializeAITree(player, PieceColor.DARK);
	}
	private void initializeAITree(AIPlayer player, PieceColor firstColor) {
		if (player instanceof AIPlayer) {
			player.setBoard(board, player.color, firstColor, MAX_TREE_DEPTH);
			player.evaluateTurns();
		}
	}
	
	private static boolean isEliminated(Player player, GameState board) {
		return board.getPieces(player.color).size() == 0;
	}

	private void resetTurn() {
		turnBeingBuilt = null;
		grabbedPiece = null;
		turnBeingBuilt = null;
	}
	
	private void filterTurns(Piece piece) {
		int pieceIndex = piece.getPosition().index;
		for (int i = filteredTurns.size() - 1; i >= 0; i--) {
			if (filteredTurns.get(i).piecePositionIndex != pieceIndex)
				filteredTurns.remove(i);
		}
	}
	
	private void setTurns() {
		filteredTurns.clear();
		for (Turn turn: possibleTurns) {
			turn.resetIterator();
			filteredTurns.add(turn);
		}
	}
	
	private void filterTurns(Position position) {
		for (int i = filteredTurns.size() - 1; i >= 0; i--) {
			Turn turn = filteredTurns.get(i);
			Position next = turn.nextMove();
			
			if (!(next.equals(position)))
				filteredTurns.remove(i);
		}
	}

	public void syncGuiWithGameState() {
		for (GuiPiece piece: guiPieces)
			piece.setCoordinates();
		view.canvas.repaint(guiPieces);
	}
	
	/**
	 * Generate a guiPieces based on pieces in gameState
	 * @param board GameState to get pieces from
	 */
	public void initializePieces(GameState board) {
		guiPieces.clear();
		pieceMap.clear();
		
		for (short i = 1; i <= GameState.NUM_POSITIONS; i++) {
			Piece piece = board.getPieceAtPosition(Position.getPosition(i));
			if (piece != null) {
				GuiPiece guiPiece = new GuiPiece(piece, BoardCanvas.TILE_SIZE);
				guiPieces.add(guiPiece);
				pieceMap.put(piece, guiPiece);
			}
		}
		view.canvas.repaint(guiPieces);
	}
	
	public void drawArrows(boolean doDraw)
	{
		if (doDraw) {
			List<Arrow> arrows = new LinkedList<Arrow>();
			for (Turn turn: filteredTurns)
			{
				Point current = view.canvas.getCoordinates(turn.getCurrentPosition());
				Point next = view.canvas.getCoordinates(turn.peekNextMove());
				
				double angle = Math.atan2(next.y - current.y, next.x - current.x);
				
				Point arrowPosition = view.canvas.getCenter(turn.getCurrentPosition());
				arrows.add(new Arrow(arrowPosition, angle));
			}
			view.canvas.setArrows(arrows);
		} else {
			view.canvas.setArrows(null);
		}
	}
	
	/**
	 * Handler for mousePress.
	 * Picks up a piece
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (canMove) {
			BoardCanvas gui = view.canvas;
			
			Position position = gui.getPosition(e.getX(), e.getY()); 
			
			if (position != null) {
				
				// if no grabbed piece, select the piece
				if (grabbedPiece == null) {
					Piece piece = board.getPieceAtPosition(position);
					if (piece != null) {
						filterTurns(piece);
						drawArrows(true);
						
						if (filteredTurns.size() > 0) {
							grabbedPiece = pieceMap.get(piece);
							grabbedPiece.setMoving(true);
							grabOffset = view.canvas.getPositionOffset(e.getX(), e.getY());
							turnBeingBuilt = new Turn(grabbedPiece.piece, position);
						}
					}
				}
				
				// otherwise, selecting new position for piece
				else {
					filterTurns(position);
					
					// if no errors
					if (filteredTurns.size() > 0) {
						
						turnBeingBuilt.addMove(position);
						
						// if we got to an end point, we are done
						if (filteredTurns.size() == 1 && !filteredTurns.get(0).hasNextMove()) {
							
							grabbedPiece.setCoordinates(view.canvas.getCoordinates(position));
							grabbedPiece = null;
							grabOffset = null;
							filteredTurns.clear();
							doTurn(turnBeingBuilt);
						}
						else
							drawArrows(true);
					}
					
					// if we went to a bad location start over
					else {
						resetTurn();
						setTurns();
						syncGuiWithGameState();
						view.canvas.repaint(guiPieces);
						drawArrows(true);
					}
					
				}
			}
		}
	}
	
	/**
	 * Handler for dragging mouse.
	 * Moves piece if one is grabbed.
	 * @param e MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (grabbedPiece != null) {
			grabbedPiece.setCoordinates(new Point(e.getX() - grabOffset.x, e.getY() - grabOffset.y));
		}
		view.canvas.repaint(guiPieces);
	}
	
	/********************************************************
	/** the stuff below is for MouseListener, but not used **
	/*******************************************************/ 
	@Override public void mouseReleased(MouseEvent e)  { }
	@Override public void mouseClicked(MouseEvent e)  { }		
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
	@Override public void mouseDragged(MouseEvent e) { }

	/**
	 * Listener for selecting menu items
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser;
		int returnValue;
		
		switch (e.getActionCommand()) {
		case CanvasView.SAVE:
			fileChooser = new JFileChooser("data");
			returnValue = fileChooser.showSaveDialog(view);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				BoardInitializerFromFile initializer = new BoardInitializerFromFile();
				try {
					initializer.getBoard(board);
					initializer.saveFile(file);
				} catch (IOException e1) {
					System.out.println("Failed to save file");
				}
			}
			break;
			
		case CanvasView.LOAD:
			fileChooser = new JFileChooser("data");
			returnValue = fileChooser.showOpenDialog(view);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				BoardInitializerFromFile initializer = new BoardInitializerFromFile();
				try {
					initializer.loadFile(file);
					initializer.setBoard(board);

					// TODO: save/load color of current player

					if (white instanceof AIPlayer)
						initializeAITree((AIPlayer)white, white.color);
					if (black instanceof AIPlayer)
						initializeAITree((AIPlayer)black, white.color);
					
					start(white);
				} catch (IOException e1) {
					System.out.println("Failed to load file");
				}
			}
			break;
			
		case CanvasView.NEW:
			Object[] options = {"Cancel", "Okay"};
			NewGameMenu newGameMenu = new NewGameMenu();
			drawArrows(false);
			int response = JOptionPane.showOptionDialog(view, newGameMenu, NEW_GAME, 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			
			if (response == 1) {
				// User chose to start a new game
				Player player1 = null;
				Player player2 = null;
				System.out.println(newGameMenu.getGameType());
				if (newGameMenu.getGameType() == NewGameMenu.LOCAL) {
					// local game
					PlayerInfo player1Info = newGameMenu.getPlayer1();
					PlayerInfo player2Info = newGameMenu.getPlayer2();

					player1 = playerFactory.getPlayer(player1Info, view.canvas);
					player2 = playerFactory.getPlayer(player2Info, view.canvas);
				} else {
					// remote game
					PlayerInfo player1Info = newGameMenu.getPlayer1();
					try {
						int lobbyPort = newGameMenu.getLocalPort();
						CheckersConnector remoteConnector = CheckersConnector.enterLobby(LOBBY_ADDRESS, LOBBY_PORT, 
								player1Info.getPlayerName(), lobbyPort);
						RemotePlayerInfo remotePlayerInfo = remoteConnector.getOpponent();
						System.out.println("opponent: "+remotePlayerInfo);
						player2 = new RemotePlayer(remoteConnector, remotePlayerInfo);
						System.out.println("opponent is " + player2.color);
						
						player1Info.setColor(PieceColor.opposite(player2.color));
						player1 = playerFactory.getPlayer(player1Info, view.canvas);
						System.out.println("You are color " + player1.color);
					} catch (IOException e1) { /* player2 doesn't get set on Exceptions*/}
				}
				
				if (player2 != null) {
					DefaultInitializer initializer = new DefaultInitializer();
					initializer.setBoard(board);
					
					if (player1 instanceof AIPlayer)
						initializeAITree((AIPlayer)player1);
					if (player2 instanceof AIPlayer)
						initializeAITree((AIPlayer)player2);
					
					start(player1, player2);
				}
			}
			
			break;
			
		case CanvasView.UNDO:
			if (undoManager.hasNextUndo()) {
				board = undoManager.undo();
				Player lastPlayer = currentPlayer;
				currentPlayer = getOpponent(currentPlayer);
				
				// go another step back if opponent is ai
				if (currentPlayer instanceof AIPlayer && undoManager.hasNextUndo()) {
					board = undoManager.undo();
					currentPlayer = getOpponent(currentPlayer);
				}
				
				initializePieces(board);
				
				if (currentPlayer instanceof AIPlayer)
					initializeAITree((AIPlayer)currentPlayer, currentPlayer.color);
				if (lastPlayer instanceof AIPlayer)
					initializeAITree((AIPlayer)lastPlayer, currentPlayer.color);
				
				giveTurn(currentPlayer);
			}
			break;
			
		case CanvasView.REDO:
			if (undoManager.hasNextRedo()) {
				board = undoManager.redo();
				Player lastPlayer = currentPlayer;
				currentPlayer = getOpponent(currentPlayer);
				
				initializePieces(board);
				
				if (currentPlayer instanceof AIPlayer)
					initializeAITree((AIPlayer)currentPlayer, currentPlayer.color);
				if (lastPlayer instanceof AIPlayer)
					initializeAITree((AIPlayer)lastPlayer, currentPlayer.color);
				
				giveTurn(currentPlayer);
			}
			break;
		}	
	}
	
	/**
	 * Turn animator
	 * Animates a turn, and then calls Demo.doTurn()
	 */
	private class TurnAnimator implements ActionListener {
		private static final double distancePerFrame = 10.0;
		private Turn turn;
		private Timer timer;
		private Point goalPoint;
		private GuiPiece movingPiece;
		
		public TurnAnimator() {
			timer = new Timer(1000 / fps, this);
		}
		
		/**
		 * Animate frame.
		 * Triggered by timer
		 * @param e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			double dist = getDistance(movingPiece.getCoordinates(), goalPoint);
			
			if (dist < distancePerFrame) {
				// put piece in place
				movingPiece.setCoordinates(goalPoint);
				view.canvas.repaint(guiPieces);
				
				// if more positions in turn, get the next one
				if (turn.hasNextMove()) {
					goalPoint = view.canvas.getCoordinates(turn.nextMove());
				}
				
				// else end the animation and call doMove
				else
				{
					timer.stop();
					doTurn(turn);
				}
			} else {
				double theta = getAngle(movingPiece.getCoordinates(), goalPoint);
				int newX = movingPiece.getCoordinates().x + (int)(Math.cos(theta) * distancePerFrame);
				int newY = movingPiece.getCoordinates().y + (int)(Math.sin(theta) * distancePerFrame);
				movingPiece.setCoordinates(new Point(newX, newY));
				view.canvas.repaint(guiPieces);
			}
		}
		
		/**
		 * Store the turn and start the timer
		 * Once the animation completes, turn is passed to doTurn()
		 * @param turn Turn to animate
		 */
		public void animateTurn(Turn turn) {
			this.turn = turn;
			int positionIndex = turn.getCurrentPosition().index;
			Piece piece = board.getPieceAtPosition(positionIndex);
			movingPiece = pieceMap.get(piece);
			goalPoint = view.canvas.getCoordinates(turn.nextMove());
			timer.start();
		}
		
		private double getDistance(Point a, Point b) {
			return Math.sqrt(Math.pow(b.x - a.x,  2) + Math.pow(b.y - a.y, 2));
		}
		
		private double getAngle(Point a, Point b) {
			return Math.atan2(b.y - a.y, b.x - a.x);
		}
	}
}

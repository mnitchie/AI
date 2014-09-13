package hoffnitch.ai.checkers;

import hoffnitch.ai.checkers.ai.RandomBot;
import hoffnitch.ai.checkers.boardSetup.BoardInitializerFromFile;
import hoffnitch.ai.checkers.boardSetup.DefaultInitializer;
import hoffnitch.ai.checkers.gui.BoardCanvas;
import hoffnitch.ai.checkers.gui.CanvasView;
import hoffnitch.ai.checkers.gui.GuiPiece;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.event.MouseInputListener;

public class Demo implements MouseInputListener, ActionListener
{
	private GameState board;
	private CheckersTurnMoveGenerator moveGenerator;
	private CanvasView view;
	private Player white;
	private Player black;
	private Player currentPlayer;

	// Gui-related stuff
	private GuiPiece grabbedPiece;
	private Point grabOffset;
	private List<GuiPiece> guiPieces;
	private Map<Piece, GuiPiece> pieceMap;
	
	// Turn-related stuff
	private Turn turnBeingBuilt;
	private List<Iterator<Position>> turnIterators;
	private List<Turn> possibleTurns;
	private boolean canMove;
	
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.start();
	}
	
	public Demo() {

		guiPieces = new LinkedList<GuiPiece>();
		pieceMap = new HashMap<Piece, GuiPiece>();
		turnIterators = new LinkedList<Iterator<Position>>();
		
		board = new GameState();
		moveGenerator = new CheckersTurnMoveGenerator(board);
		view = new CanvasView("Checkers", this);
		
		view.canvas.addMouseListener(this);
		view.canvas.addMouseMotionListener(this);
		
		// human player
		white = new HumanPlayer("Mike", PieceColor.LIGHT, view.canvas);
		
		// AI Player
		black = new RandomBot("Brad", PieceColor.DARK);
		
		view.setVisible(true);
	}
	
	private void giveTurn(Player player) {
		
		currentPlayer = player;
		
		if (isEliminated(player, board))
			endGame(getOpponent(player));
		
		else {
			List<Turn> validTurns = moveGenerator.getMovesForTurn(player.color);
			if (validTurns.size() == 0)
				endGame(getOpponent(player));
			else {
				canMove = false;
				
				// if ai, turn will be determined now
				if (player instanceof AIPlayer)
					doTurn(((AIPlayer) player).getTurn(validTurns));
				
				// if human, enable moving pieces and wait for event to call doTurn
				else {
					possibleTurns = validTurns;
					canMove = true;
				}
			}
		}
	}
	
	private void endGame(Player winner) {
		System.out.println(winner.color.toString() + " wins");
	}
	
	private void doTurn(Turn turn) {
		view.textArea.append(turn.toString() + "\n");
		
		board.doTurn(turn);
		syncGuiWithGameState();
		giveTurn(getOpponent(currentPlayer));
	}
	
	private Player getOpponent(Player player) {
		if (player == white)
			return black;
		else
			return white;
	}
	
	public void start() {
		initializePieces(board);
		giveTurn(black);
	}
	
	private static boolean isEliminated(Player player, GameState board) {
		return board.getPieces(player.color).size() == 0;
	}

	private void resetTurn() {
		turnBeingBuilt = null;
		grabbedPiece = null;
		turnBeingBuilt = null;
	}
	
	private void setBaseIterators(Piece piece) {
		turnIterators.clear();
		for (Turn turn: possibleTurns)
			if (turn.piece == piece) {
				Iterator<Position> iterator = turn.iterator();
				turnIterators.add(iterator);
				iterator.next();
			}
	}
	
	private void filterItertors(Position position) {
		for (int i = turnIterators.size() - 1; i >= 0; i--) {
			Iterator<Position> iterator = turnIterators.get(i);
			Position next = iterator.next();
			
			if (!(next.equals(position)))
				turnIterators.remove(i);
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
			Piece piece = board.getPieceAtPosition(new Position(i));
			if (piece != null) {
				GuiPiece guiPiece = new GuiPiece(piece, BoardCanvas.TILE_SIZE);
				guiPieces.add(guiPiece);
				pieceMap.put(piece, guiPiece);
			}
		}
		view.canvas.repaint(guiPieces);
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
						setBaseIterators(piece);
						
						if (turnIterators.size() > 0) {
							grabbedPiece = pieceMap.get(piece);
							grabbedPiece.setMoving(true);
							grabOffset = view.canvas.getPositionOffset(e.getX(), e.getY());
							turnBeingBuilt = new Turn(grabbedPiece.piece, position);
						}
					}
				}
				
				// otherwise, selecting new position for piece
				else {
					filterItertors(position);
					
					// if no errors
					if (turnIterators.size() > 0) {
						
						turnBeingBuilt.addMove(position);
						
						// if we got to an end point, we are done
						if (turnIterators.size() == 1 && !turnIterators.get(0).hasNext()) {
							
							grabbedPiece = null;
							grabOffset = null;
							doTurn(turnBeingBuilt);
						}
						
					}
					
					// if we went to a bad location start over
					else {
						resetTurn();
						syncGuiWithGameState();
						view.canvas.repaint(guiPieces);
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
	public void actionPerformed(ActionEvent e)
	{
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
					initializePieces(board);
					// TODO: save/load color of current player
					giveTurn(white);
				} catch (IOException e1) {
					System.out.println("Failed to load file");
				}
			}
			break;
		case CanvasView.NEW:
			DefaultInitializer initializer = new DefaultInitializer();
			initializer.setBoard(board);
			start();
			break;
		}	
	}
}

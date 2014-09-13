package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.View;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class CanvasView extends JFrame implements View {
	private static final long serialVersionUID = -5448930846329842670L;

	public static final String LOAD = "Load";
	public static final String SAVE = "Save";
	public static final String NEW = "New";
	public static final String UNDO = "Undo";
	public static final String REDO = "Redo";
	
	public static final int WIDTH	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	public static final int HEIGHT	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	
	private JMenuBar menuBar;
	private ActionListener listener;
	
	public final BoardCanvas canvas;
	public final JTextArea textArea;
	
	public CanvasView(String title, ActionListener listener) {
		super(title);
		
		this.listener = listener;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas();
		add(canvas, BorderLayout.CENTER);
		
		makeMenu();
		textArea = new JTextArea(10, 10);
		makeTextArea();
		
		pack();
		// set fixed window size
		Insets insets = getInsets();
		// I don't know why "+4". The text area overlaps the board without it.
		setSize(WIDTH + insets.left + insets.right, HEIGHT + insets.top + menuBar.getBounds().height + textArea.getHeight() + 4);
		setResizable(false);
	}
	
	private void makeTextArea() {
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    textArea.setEditable(false);
	    add(scrollPane, BorderLayout.PAGE_END);
	}
	
	private void makeMenu() {
		
		menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		JMenu boardMenu = new JMenu("Board");
		menuBar.add(boardMenu);
		
		// new
		JMenuItem newGame = new JMenuItem(NEW, KeyEvent.VK_N); 
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGame.getAccessibleContext().setAccessibleDescription("Reset board");
		gameMenu.add(newGame);
		newGame.addActionListener(listener);
		
		// undo
		JMenuItem undo = new JMenuItem(UNDO, KeyEvent.VK_Z); 
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undo.getAccessibleContext().setAccessibleDescription("Undo last move");
		gameMenu.add(undo);
		undo.addActionListener(listener);
		
		// redo
		JMenuItem redo = new JMenuItem(UNDO, KeyEvent.VK_Y); 
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		redo.getAccessibleContext().setAccessibleDescription("Redo move");
		gameMenu.add(redo);
		redo.addActionListener(listener);
		
		// load
		JMenuItem load = new JMenuItem(LOAD, KeyEvent.VK_O); 
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		load.getAccessibleContext().setAccessibleDescription("Load a board configuration");
		boardMenu.add(load);
		load.addActionListener(listener);
		
		// save
		JMenuItem save = new JMenuItem(SAVE, KeyEvent.VK_S); 
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.getAccessibleContext().setAccessibleDescription("Save a board configuration");
		boardMenu.add(save);
		save.addActionListener(listener);
		
		setJMenuBar(menuBar);
	}
	
}

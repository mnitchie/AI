package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.View;
import hoffnitch.ai.checkers.boardSetup.BoardInitializerFromFile;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
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
	
	public static final int WIDTH	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	public static final int HEIGHT	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	
	private JMenuBar menuBar;
	private JMenu menu;
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
		// menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Board");
		menuBar.add(menu);
		
		final CanvasView parent = this;
		
		// load
		JMenuItem load = new JMenuItem(LOAD, KeyEvent.VK_L); 
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.getAccessibleContext().setAccessibleDescription("Load a board configuration");
		menu.add(load);
		load.addActionListener(listener);
		
		// save
		JMenuItem save = new JMenuItem(SAVE, KeyEvent.VK_S); 
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.getAccessibleContext().setAccessibleDescription("Save a board configuration");
		menu.add(save);
		save.addActionListener(listener);
		
		setJMenuBar(menuBar);
	}
	
	public JMenu getMenu() {
		return menu;
	}
	
}

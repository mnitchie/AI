package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Turn;
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
	
	public static final int WIDTH	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	public static final int HEIGHT	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	
	private JMenuBar menuBar;
	private JMenu menu;
	
	public final BoardCanvas canvas;
	public final JTextArea textArea;
	private GameState board;
	
	public CanvasView(String title, GameState initialBoard) {
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.board = initialBoard;
		canvas = new BoardCanvas(initialBoard);
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
	
	@Override
	public Turn getTurn()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBoard(Turn turn)
	{
		// TODO Auto-generated method stub
		
	}

	private void makeMenu() {
		// menu bar
		menuBar = new JMenuBar();
		menu = new JMenu("Board");
		menuBar.add(menu);
		
		final CanvasView parent = this;
		
		// load
		JMenuItem load = new JMenuItem("Load", KeyEvent.VK_L); 
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.getAccessibleContext().setAccessibleDescription("Load a board configuration");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("data");
				int returnValue = fileChooser.showOpenDialog(parent);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					BoardInitializerFromFile initializer = new BoardInitializerFromFile();
					try {
						initializer.loadFile(file);
						initializer.setBoard(parent.board);
						//canvas.initializePieces(parent.board);
					} catch (IOException e1) {
						System.out.println("Failed to load file");
					}
				}
			}
		});
		menu.add(load);
		
		// save
		JMenuItem save = new JMenuItem("Save", KeyEvent.VK_S); 
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.getAccessibleContext().setAccessibleDescription("Save a board configuration");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("data");
				int returnValue = fileChooser.showSaveDialog(parent);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					BoardInitializerFromFile initializer = new BoardInitializerFromFile();
					try {
						initializer.getBoard(parent.board);
						initializer.saveFile(file);
					} catch (IOException e1) {
						System.out.println("Failed to save file");
					}
				}
			}
		});
		menu.add(save);
		
		setJMenuBar(menuBar);
	}
	
	
	
}

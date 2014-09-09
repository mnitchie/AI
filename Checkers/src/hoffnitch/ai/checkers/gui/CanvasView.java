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
import javax.swing.KeyStroke;

public class CanvasView extends JFrame implements View {
	private static final long serialVersionUID = -5448930846329842670L;
	
	public static final int WIDTH	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	public static final int HEIGHT	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	
	private JMenuBar menuBar;
	private JMenu menu;
	
	private BoardCanvas canvas;
	private GameState board;
	
	public CanvasView(String title, GameState initialBoard) {
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.board = initialBoard;
		canvas = new BoardCanvas(initialBoard);
		add(canvas);
		
		makeMenu();
		
		pack();
		// set fixed window size
		Insets insets = getInsets();
		setSize(WIDTH + 2, HEIGHT + insets.top + menuBar.getBounds().height);
		setResizable(false);
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
		JMenuItem load = new JMenuItem("load", KeyEvent.VK_L); 
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.getAccessibleContext().setAccessibleDescription("Load a board configuration");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("load");
				JFileChooser fileChooser = new JFileChooser("data");
				int returnValue = fileChooser.showOpenDialog(parent);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					BoardInitializerFromFile initializer = new BoardInitializerFromFile();
					try {
						initializer.loadFile(file);
						initializer.setBoard(parent.board);
					} catch (IOException e1) {
						System.out.println("Failed to load file");
					}
				}
			}
		});
		menu.add(load);
		
		setJMenuBar(menuBar);
	}
	
	
	
}

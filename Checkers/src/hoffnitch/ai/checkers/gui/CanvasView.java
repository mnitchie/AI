package hoffnitch.ai.checkers.gui;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.View;
import hoffnitch.ai.checkers.gui.CanvasView_old.GuiPiece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;

public class CanvasView extends JFrame implements View
{
	public static final int WIDTH	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	public static final int HEIGHT	= GameState.WIDTH * BoardCanvas.TILE_SIZE;
	
	private BoardCanvas canvas;
	
	public CanvasView(String title, GameState initialBoard) {
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas(initialBoard);
		add(canvas);
		
		pack();
		
		// the +2 is a mystery. Otherwise, just accounting for the bar at the top
		Insets insets = getInsets();
		setSize(WIDTH + 2, HEIGHT + insets.top);
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

	
	
}

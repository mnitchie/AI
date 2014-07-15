package hoffnitch.ai.gameBasics;

import hoffnitch.ai.interfaces.PlayerAdapter;

import java.util.Scanner;

public class Player extends GameModelHolder implements PlayerAdapter
{
	private static final Scanner keyboard = new Scanner(System.in);
	@Override
	public Color getColor()
	{
		return this.playerA.getColor();
	}

	@Override
	public Action promptForMove(GameState gameState)
	{	
		int startRow, endRow, startCol, endCol;
		
		System.out.println("Player " + playerA.getColor().getName() + ", it is your move");
		
		System.out.println("Enter the row number of the piece to move");
		startRow = keyboard.nextInt();
		
		System.out.println("Enter the column number of the piece to move");
		startCol = keyboard.nextInt();
		
		System.out.println("Enter the row number of the destination");
		endRow = keyboard.nextInt();
		
		System.out.println("Enter the column number of the destination");
		endCol = keyboard.nextInt();
		
		Piece p = gameState.getPiece(new Position(startRow, startCol));
		
		Action action = new Action(p, new Position(endRow, endCol));
		
		return action;
	}

	@Override
	public void updateGameState(Action action)
	{		
		gameState.setPiece(null, action.getPiece().getPosition());
		gameState.setPiece(action.getPiece(), action.getMoveChain().getPosition());	
	}

}

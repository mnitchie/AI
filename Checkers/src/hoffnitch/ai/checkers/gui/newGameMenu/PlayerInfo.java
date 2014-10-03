package hoffnitch.ai.checkers.gui.newGameMenu;

import hoffnitch.ai.checkers.PieceColor;

public class PlayerInfo
{
	private PieceColor color;
	private String playerName;
	private String playerType;
	private boolean isLocal;
	public PieceColor getColor()
	{
		return color;
	}
	public void setColor(PieceColor color)
	{
		this.color = color;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	public String getPlayerType()
	{
		return playerType;
	}
	public void setPlayerType(String playerType)
	{
		this.playerType = playerType;
	}
	public boolean isLocal()
	{
		return isLocal;
	}
	public void setLocal(boolean isLocal)
	{
		this.isLocal = isLocal;
	}
	
	public String toString() {
		return playerName + "[" + color + "] " + playerType; 
	}
	
}

package hoffnitch.ai.checkers.ai;

import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;
import hoffnitch.ai.checkers.Turn;
import hoffnitch.ai.checkers.exceptions.InvalidTurnException;

import java.util.List;

import checkersRemote.CheckersConnector;
import checkersRemote.RemotePlayerInfo;

public class RemotePlayer extends NonHumanPlayer {

	private CheckersConnector remoteConnector;
	private RemotePlayerInfo remotePlayerInfo;
	
	public RemotePlayer(CheckersConnector remoteConnector, RemotePlayerInfo remotePlayerInfo) {
		super(remotePlayerInfo.name, getPieceColor(remotePlayerInfo));
		this.remoteConnector = remoteConnector;
		this.remotePlayerInfo = remotePlayerInfo;
	}
	
	private static PieceColor getPieceColor(RemotePlayerInfo remotePlayerInfo) {
		if (remotePlayerInfo.color.equals(RemotePlayerInfo.BLACK))
			return PieceColor.DARK;
		else
			return PieceColor.LIGHT;
	}
	
	public String getOpponentAction() {
		return remoteConnector.getOpponentAction();
	}
	
	@Override
	public Turn getTurn(/*GameState board*/) {
		int[] moveArray = remoteConnector.getMove();
		
		Turn turn = new Turn(moveArray[0], color);
		
		for (int i = 0; i < moveArray.length; i++)
			turn.addMove(Position.getPosition(moveArray[i]));
		
		return turn;
	}
	
	@Override
	public void receiveOpponentTurn(Turn turn) throws InvalidTurnException {
		List<Position> moves = turn.getMoves();
		int[] moveArray = new int[moves.size()];
		for (int i = 0; i < moves.size(); i++) {
			moveArray[i] = moves.get(i).index;
		}
		remoteConnector.sendMove(moveArray);
	}

	public String getName() {
		return remotePlayerInfo.name;
	}

}

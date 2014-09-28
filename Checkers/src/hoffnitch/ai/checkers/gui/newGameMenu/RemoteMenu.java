package hoffnitch.ai.checkers.gui.newGameMenu;

import java.awt.BorderLayout;

import hoffnitch.ai.checkers.ai.PlayerFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RemoteMenu extends JPanel {
	private static final long serialVersionUID = -6955736769486830188L;

	private static final String DEFAULT_NAME	= "Local Player";
	private static final Integer DEFAULT_LOBBY_PORT = 5556;
	
	private PlayerPanel localPlayerPanel;
	private PlayerInfo remotePlayerInfo;
	private JTextField portField;
	
	public RemoteMenu() {
		setLayout(new BorderLayout());
		
		// port box
		JPanel namePanel = new JPanel();
		JLabel portLabel = new JLabel("local port");
		namePanel.add(portLabel);
		portField = new JTextField(DEFAULT_LOBBY_PORT.toString(), 5);
		namePanel.add(portField);
		add(namePanel, BorderLayout.PAGE_START);
		
		localPlayerPanel = new PlayerPanel(DEFAULT_NAME, null);
		add(localPlayerPanel);
		
		remotePlayerInfo = new PlayerInfo();
		remotePlayerInfo.setColor(null);
		remotePlayerInfo.setPlayerName(null);
		remotePlayerInfo.setPlayerType(PlayerFactory.REMOTE_PLAYER);
		remotePlayerInfo.setLocal(false);
		
	}
	
	public PlayerInfo getPlayer1() {
		return localPlayerPanel.getPlayerInfo();
	}
	
	public PlayerInfo getPlayer2() {
		return remotePlayerInfo;
	}
	
	public int getLobbyPort() {
		return Integer.parseInt(portField.getText());
	}
}

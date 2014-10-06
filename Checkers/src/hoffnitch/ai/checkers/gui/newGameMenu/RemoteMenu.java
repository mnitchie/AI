package hoffnitch.ai.checkers.gui.newGameMenu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import hoffnitch.ai.checkers.ai.PlayerFactory;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RemoteMenu extends JPanel {
	private static final long serialVersionUID = -6955736769486830188L;

	private static final String DEFAULT_NAME	= "Local Player";
	private static final Integer DEFAULT_LOCAL_PORT = 5556;
	private static final Integer DEFAULT_LOBBY_PORT = 5555;
	private static final String DEFAULT_LOBBY_ADDRESS = "127.0.0.1";
	
	private PlayerPanel localPlayerPanel;
	private PlayerInfo remotePlayerInfo;
	private JTextField localPortField;
	private JTextField lobbyPortField;
	private JTextField lobbyAddressField;
	
	public RemoteMenu() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// port box
		JPanel localPortPanel = new JPanel();
		JPanel lobbyAddressPanel = new JPanel();
		JPanel lobbyPortPanel = new JPanel();
		JLabel localPortLabel = new JLabel("Local port");
		JLabel lobbyAddressLabel = new JLabel("Lobby address");
		JLabel lobbyPortLabel = new JLabel("Lobby port");
		localPortField = new JTextField(DEFAULT_LOCAL_PORT.toString(), 6);
		lobbyPortField = new JTextField(DEFAULT_LOBBY_PORT.toString(), 6);
		lobbyAddressField = new JTextField(DEFAULT_LOBBY_ADDRESS.toString(), 6);
		localPortPanel.add(localPortLabel);
		localPortPanel.add(localPortField);
		lobbyAddressPanel.add(lobbyAddressLabel);
		lobbyAddressPanel.add(lobbyAddressField);
		lobbyPortPanel.add(lobbyPortLabel);
		lobbyPortPanel.add(lobbyPortField);
		add(localPortPanel, BorderLayout.PAGE_START);
		add(lobbyAddressPanel, BorderLayout.PAGE_START);
		add(lobbyPortPanel, BorderLayout.PAGE_START);
		
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
	
	public int getLocalPort() {
		return Integer.parseInt(localPortField.getText());
	}
	
	public int getLobbyPort() {
		return Integer.parseInt(lobbyPortField.getText());
	}
	
	public String getLobbyAddress() {
		return lobbyAddressField.getText();
	}
}

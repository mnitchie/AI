package hoffnitch.ai.checkers.gui.newGameMenu;

import hoffnitch.ai.checkers.PieceColor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LocalMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = -4450341440113224802L;
	
	private static final String TOGGLE_COLORS = "Toggle colors";

	private static final String DEFAULT_P1_NAME	= "Mike";
	private static final String DEFAULT_P2_NAME	= "Tyler";
	
	private JPanel players;
	private PlayerPanel player1Panel;
	private PlayerPanel player2Panel;
	
	public LocalMenu() {
		setLayout(new BorderLayout());
		JButton colorToggler = new JButton(TOGGLE_COLORS);
		JPanel togglerPanel = new JPanel();
		togglerPanel.add(colorToggler);
		add(togglerPanel, BorderLayout.PAGE_END);
		colorToggler.addActionListener(this);
		
		players = new JPanel();
		players.setLayout(new GridLayout(1, 2));
		add(players);
		
		player1Panel = new PlayerPanel(DEFAULT_P1_NAME, PieceColor.DARK);
		player2Panel = new PlayerPanel(DEFAULT_P2_NAME, PieceColor.LIGHT);
		players.add(player1Panel);
		players.add(player2Panel);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand()) {
		case TOGGLE_COLORS:
			PieceColor temp = player1Panel.getColor();
			player1Panel.setColor(player2Panel.getColor());
			player2Panel.setColor(temp);
		}	
	}
	
	public PlayerInfo getPlayer1() {
		return player1Panel.getPlayerInfo();
	}
	
	public PlayerInfo getPlayer2() {
		return player2Panel.getPlayerInfo();
	}
}

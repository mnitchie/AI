package hoffnitch.ai.checkers.gui.newGameMenu;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class NewGameMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2009215417853671954L;

	public static final String LOCAL = "Local";
	public static final String REMOTE = "Remote";

	private JRadioButton local;
	private JRadioButton remote;
	private String gameType;
	
	private JPanel gameTypeChoicePanel;
	private JPanel gamePanel;
	private CardLayout gameTypeLayout;
	
	public NewGameMenu() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 170));
		
		// default game type
		gameType = LOCAL;
		
		// setup local or remote panel
		gameTypeChoicePanel = new JPanel();
		ButtonGroup localOrRemoteGroup = new ButtonGroup();
		local = new JRadioButton(LOCAL, true);
		remote = new JRadioButton(REMOTE);
		local.addActionListener(this);
		remote.addActionListener(this);
		localOrRemoteGroup.add(local);
		localOrRemoteGroup.add(remote);
		gameTypeChoicePanel.add(local);
		gameTypeChoicePanel.add(remote);
		
		add(gameTypeChoicePanel, BorderLayout.PAGE_START);
		
		gameTypeLayout = new CardLayout();
		gamePanel = new JPanel(gameTypeLayout);
		gamePanel.add(new LocalMenu(), LOCAL);
		gamePanel.add(new RemoteMenu(), REMOTE);
		
		add(gamePanel);
	}

	/**
	 * Handle all actions
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gameTypeLayout.show(gamePanel, e.getActionCommand());
	}
	
	public String getGameType() {
		return gameType;
	}
	
}

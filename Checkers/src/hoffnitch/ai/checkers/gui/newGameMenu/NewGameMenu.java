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

	private ButtonGroup localOrRemoteGroup;
	private JRadioButton local;
	private JRadioButton remote;
	
	private JPanel localOrRemoteChoicePanel;
	private JPanel localOrRemotePanel;
	private CardLayout localOrRemoteLayout;
	
	public NewGameMenu() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 170));
		
		// setup local or remote panel
		localOrRemoteChoicePanel = new JPanel();
		localOrRemoteGroup = new ButtonGroup();
		local = new JRadioButton(LOCAL, true);
		remote = new JRadioButton(REMOTE);
		local.addActionListener(this);
		remote.addActionListener(this);
		localOrRemoteGroup.add(local);
		localOrRemoteGroup.add(remote);
		localOrRemoteChoicePanel.add(local);
		localOrRemoteChoicePanel.add(remote);
		
		add(localOrRemoteChoicePanel, BorderLayout.PAGE_START);
		
		localOrRemoteLayout = new CardLayout();
		localOrRemotePanel = new JPanel(localOrRemoteLayout);
		localOrRemotePanel.add(new LocalMenu(), LOCAL);
		localOrRemotePanel.add(new RemoteMenu(), REMOTE);
		
		add(localOrRemotePanel);
	}

	/**
	 * Handle all actions
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		localOrRemoteLayout.show(localOrRemotePanel, e.getActionCommand());
	}
	
}

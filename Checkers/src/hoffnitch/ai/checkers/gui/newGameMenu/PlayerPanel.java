package hoffnitch.ai.checkers.gui.newGameMenu;

import hoffnitch.ai.checkers.PieceColor;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 886308789361598417L;

	private static final int NAME_FIELD_COLUMNS = 15;
	
	// TODO: Generate player type list elsewhere
	private static final String HUMAN = "Human";
	private static final String RANDOM_BOT = "Randombot";
	private static final String FIRST_BOT = "Firstbot";
	private static final String[] PLAYER_TYPES = {HUMAN, RANDOM_BOT, FIRST_BOT};

	private JLabel colorLabel;
	private JTextField nameField;
	private JComboBox<String> playerTypes;
	
	public PlayerPanel(PieceColor color, String name) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// color label
		colorLabel = new JLabel(color.toString());
		add(colorLabel);
		
		// name box
		JPanel namePanel = new JPanel();
		nameField = new JTextField(name, NAME_FIELD_COLUMNS);
		namePanel.add(nameField);
		add(namePanel);
		
		// type selector
		JPanel typePanel = new JPanel();
		playerTypes = new JComboBox<String>(PLAYER_TYPES);
		typePanel.add(playerTypes);
		add(typePanel);
		
	}
	
	public void setColor(PieceColor color) {
		colorLabel.setText(color.toString());
	}
}

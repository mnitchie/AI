package hoffnitch.ai.checkers.gui.newGameMenu;

import hoffnitch.ai.checkers.PieceColor;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PlayerPanel handles
 */
public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 886308789361598417L;

	private static final int NAME_FIELD_COLUMNS = 15;
	private static final boolean IS_LOCAL = true;
	
	// TODO: Generate player type list elsewhere
	private static final String HUMAN = "Human";
	private static final String RANDOM_BOT = "Randombot";
	private static final String FIRST_BOT = "Firstbot";
	private static final String[] PLAYER_TYPES = {HUMAN, RANDOM_BOT, FIRST_BOT};

	private PlayerInfo playerInfo;
	
	private JLabel colorLabel;
	private JTextField nameField;
	private JComboBox<String> playerTypes;
	
	/**
	 * Constructor for PlayerPanel
	 * @param color Default color (use null if color isn't selectable)
	 * @param name Default name
	 */
	public PlayerPanel(String name, PieceColor color) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		playerInfo = new PlayerInfo();
		playerInfo.setLocal(IS_LOCAL);
		
		// color label (only added/usable if color != null)
		if (color != null) {
			colorLabel = new JLabel();
			add(colorLabel);
			setColor(color);
		}
		
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
	
	/**
	 * Set player's color
	 * @param color Color
	 */
	public void setColor(PieceColor color) {
		this.playerInfo.setColor(color);
		colorLabel.setText(color.toString());
	}
	
	public PieceColor getColor() {
		return playerInfo.getColor();
	}
	
	public PlayerInfo getPlayerInfo() {
		
		// populate the 2 missing fields
		playerInfo.setPlayerName(nameField.getText());
		playerInfo.setPlayerType((String)playerTypes.getSelectedItem());
		
		return playerInfo;
	}
	
	
}

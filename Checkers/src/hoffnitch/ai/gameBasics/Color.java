package hoffnitch.ai.gameBasics;

public enum Color {
	BLACK("Black"),
	RED("Red")
	;
	
	private String name;
	
	Color(String name) {
		this.setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

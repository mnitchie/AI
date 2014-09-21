package hoffnitch.ai.checkers.db;

public enum GameResult {
    DARK_WIN("Dark"),
    LIGHT_WIN("Light"),
    DRAW("Draw");
    
    private String description;
    
    GameResult(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

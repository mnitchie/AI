package hoffnitch.ai.checkers.db;

import hoffnitch.ai.checkers.Turn;

import java.util.ArrayList;
import java.util.List;

public class Game {
        
    private String lightPlayer;
    
    private String darkPlayer;
    
    private GameResult result;
    
    private List<Turn> turns;
    
    private int numMoves;
    
    public Game(String lightPlayer, String darkPlayer) {
        this.lightPlayer = lightPlayer;
        this.darkPlayer = darkPlayer;
        turns = new ArrayList<Turn>();
    }
    
    public Game(String lightPlayer, String darkPlayer, List<Turn> turns) {
        this(lightPlayer, darkPlayer);
        this.turns = turns == null ? new ArrayList<Turn>() : turns;
    }
    
    public void addTurn(Turn t) {
        turns.add(t);
    }
    
    public void setLightPlayer(String lightPlayer) {
        this.lightPlayer = lightPlayer;
    }
    
    public String getLightPlayer() {
        return lightPlayer;
    }
    
    public void setDarkPlayer(String darkPlayer) {
        this.darkPlayer = darkPlayer;
    }
    
    public String getDarkPlayer() {
        return darkPlayer;
    }
    
    public void setTurns(List<Turn> turns) {
        this.turns = turns == null ? new ArrayList<Turn>() : turns;
    }
    
    public List<Turn> getTurns() {
        return turns;
    }
    
    public void setResult(GameResult result) {
        this.result = result;
    }
    
    public GameResult getResult() {
        return result;
    }
    
    public void setNumMoves() {
        numMoves = turns.size();
    }
    
    public int getNumMoves() {
        numMoves = turns.size();
        return numMoves;
    }
}

package hoffnitch.ai.checkers.db;

import hoffnitch.ai.checkers.Player;
import hoffnitch.ai.checkers.Turn;

import java.util.ArrayList;
import java.util.List;

public class Game {
        
    private Player lightPlayer;
    
    private Player darkPlayer;
    
    private List<Turn> turns;
    
    public Game(Player lightPlayer, Player darkPlayer) {
        this.lightPlayer = lightPlayer;
        this.darkPlayer = darkPlayer;
        turns = new ArrayList<Turn>();
    }
    
    public Game(Player lightPlayer, Player darkPlayer, List<Turn> turns) {
        this(lightPlayer, darkPlayer);
        this.turns = turns == null ? new ArrayList<Turn>() : turns;
    }
    
    public void addTurn(Turn t) {
        turns.add(t);
    }
    
    public void setLightPlayer(Player lightPlayer) {
        this.lightPlayer = lightPlayer;
    }
    
    public Player getLightPlayer() {
        return lightPlayer;
    }
    
    public void setDarkPlayer(Player darkPlayer) {
        this.darkPlayer = darkPlayer;
    }
    
    public Player getDarkPlayer() {
        return darkPlayer;
    }
    
    public void setTurns(List<Turn> turns) {
        this.turns = turns == null ? new ArrayList<Turn>() : turns;
    }
}

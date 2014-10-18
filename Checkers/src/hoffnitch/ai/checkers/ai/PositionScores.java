package hoffnitch.ai.checkers.ai;

public class PositionScores {
    
    private double blackPawnScore;
    private double blackKingScore;
    private double redPawnScore;
    private double redKingScore;
    
    public PositionScores() {
        blackPawnScore = 0;
        blackKingScore = 0;
        redPawnScore = 0;
        redKingScore = 0;
    }
    
    public void alterBlackPawnScore(double score) {
        blackPawnScore += score;
    }
    
    public void alterBlackKingScore(double score) {
        blackKingScore += score;
    }
    
    public void alterRedPawnScore(double score) {
        redPawnScore += score;
    }
    
    public void alterRedKingScore(double score) {
        redKingScore += score;
    }
    
    public double getBlackPawnScore() {
        return blackPawnScore;
    }
    
    public double getBlackKingScore() {
        return blackKingScore;
    }
    
    public double getRedPawnScore() {
        return redPawnScore;
    }
    
    public double getRedKingScore() {
        return redKingScore;
    }
}

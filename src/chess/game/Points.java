package chess.game;


import chess.pieces.Piece;
import chess.score.*;

public class Points {
    private int currentGameScore = 0;

    public void onCapture(Piece captured) {
        if (captured == null) return;

        ScoreStrategy strategy = new CaptureScoreStrategy();
        currentGameScore += strategy.calculateScore(captured);
    }

    public void onCheckmate(Player winner, Player loser) {

        if (winner != null) {
            ScoreStrategy winStrategy = new CheckmateScoreStrategy(true);
            int winScore = winStrategy.calculateScore(null);
            winner.setPoints(winner.getPoints() + currentGameScore + winScore);
        }

        if (loser != null) {
            ScoreStrategy loseStrategy = new CheckmateScoreStrategy(false);
            int loseScore = loseStrategy.calculateScore(null);
            loser.setPoints(loser.getPoints() + loseScore);
        }
    }

    public void onResign(Player resigning, Player other) {

        if (resigning != null) {
            ScoreStrategy loseStrategy = new ResignScoreStrategy(true);
            int loseScore = loseStrategy.calculateScore(null);
            resigning.setPoints(resigning.getPoints() + loseScore);
        }

        if (other != null) {
            ScoreStrategy winStrategy = new ResignScoreStrategy(false);
            int winScore = winStrategy.calculateScore(null);
            other.setPoints(other.getPoints() + currentGameScore + winScore);
        }
    }


    public void onDraw(Player player) {
        ScoreStrategy strategy = new DrawScoreStrategy();
        int delta = strategy.calculateScore(null);

        player.setPoints(player.getPoints() + currentGameScore + delta);
    }

    public int getCurrentGameScore() {
        return currentGameScore;
    }

    public void reset() {
        currentGameScore = 0;
    }
}

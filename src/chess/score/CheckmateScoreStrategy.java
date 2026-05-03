package chess.score;

import chess.pieces.Piece;

public class CheckmateScoreStrategy implements ScoreStrategy {
    private final boolean playerWon;

    public CheckmateScoreStrategy(boolean playerWon) {
        this.playerWon = playerWon;
    }

    @Override
    public int calculateScore(Piece piece) {
        if (playerWon) {
            return 300;
        } else {
            return -300;
        }
    }
}

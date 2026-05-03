package chess.score;

import chess.pieces.Piece;

public class ResignScoreStrategy implements ScoreStrategy {
    private final boolean resign;
    public ResignScoreStrategy(boolean resign) {
        this.resign = resign;
    }

    public int calculateScore(Piece piece) {
        if (resign) {
            return -150;
        } else {
            return 150;
        }
    }
}

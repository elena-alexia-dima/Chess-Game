package chess.score;

import chess.pieces.Piece;

public class DrawScoreStrategy implements ScoreStrategy {
    @Override
    public int calculateScore(Piece piece) {
        return 150;
    }
}

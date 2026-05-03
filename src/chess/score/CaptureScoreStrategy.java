package chess.score;

import chess.pieces.Piece;

public class CaptureScoreStrategy implements ScoreStrategy {
    public int calculateScore(Piece piece) {
        if (piece == null) {
            return 0;
        }

        return switch (piece.type()) {
            case 'Q' -> 90;
            case 'R' -> 50;
            case 'B' -> 30;
            case 'N' -> 30;
            case 'P' -> 10;
            default -> 0;
        };
    }
}

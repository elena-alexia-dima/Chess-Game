package chess.score;

import chess.pieces.Piece;

public interface ScoreStrategy {
    int calculateScore(Piece piece);
}

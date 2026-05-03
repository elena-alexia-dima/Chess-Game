package chess.pieces;

import chess.board.Board;
import chess.model.Position;

import java.util.List;

public interface ChessPiece {
    List<Position> getPossibleMoves(Board board);
    boolean checkForCheck(Board board,
                          Position kingPosition);
    char type();
}

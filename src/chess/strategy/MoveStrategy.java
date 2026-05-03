package chess.strategy;

import chess.board.Board;
import chess.model.Position;
import chess.pieces.Piece;

import java.util.List;

public interface MoveStrategy {
    List<Position> getPossibleMoves(Board board, Position from);
}

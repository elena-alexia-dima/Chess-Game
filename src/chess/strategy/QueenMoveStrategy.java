package chess.strategy;

import chess.board.Board;
import chess.model.Position;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class QueenMoveStrategy implements MoveStrategy{
    public List<Position> getPossibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPieceAt(from);
        int[] x = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] y = {0, 0, -1, 1, -1, 1, -1, 1};

        for (int dir = 0; dir < 8; dir++) {
            for (int step = 1; step < 8; step++) {
                char newX = (char)(from.getX() + x[dir] * step);
                int newY = from.getY() + y[dir] * step;

                if (newX < 'A' || newX > 'H' || newY < 1 || newY > 8) {
                    break;
                }

                Position newPos = new Position(newX, newY);
                Piece p = board.getPieceAt(newPos);

                if (p == null) {
                    moves.add(newPos);
                } else {
                    if (p.getColor() != piece.getColor()) {
                        moves.add(newPos);
                    }
                    break;
                }
            }
        }
        return moves;
    }
}

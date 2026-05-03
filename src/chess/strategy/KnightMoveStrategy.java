package chess.strategy;

import chess.board.Board;
import chess.model.Position;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveStrategy implements MoveStrategy {
    public List<Position> getPossibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPieceAt(from);
        int[] x = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] y = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            char newX = (char)(from.getX() + x[i]);
            int newY = from.getY() + y[i];

            if (newX >= 'A' && newX <= 'H' && newY >= 1 && newY <= 8) {
                Position newPos = new Position(newX, newY);
                Piece p = board.getPieceAt(newPos);

                if (p == null || p.getColor() != piece.getColor()) {
                    moves.add(newPos);
                }
            }
        }
        return moves;
    }
}

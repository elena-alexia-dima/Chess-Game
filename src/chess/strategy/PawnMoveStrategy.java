package chess.strategy;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Pawn;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy {
    public List<Position> getPossibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPieceAt(from);
        if(!(piece instanceof Pawn pawn)) {
            return moves;
        }
        int direction;
        if (piece.getColor() == Colors.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        char newX = from.getX();
        int newY = from.getY() + direction;
        if (newY >= 1 && newY <= 8) {
            Position forwardOne = new Position(newX, newY);
            if (board.getPieceAt(forwardOne) == null) {
                moves.add(forwardOne);
                if (pawn.isFirstMove()) {
                    int newY2 = from.getY() + direction * 2;
                    if (newY2 > 1 && newY2 <= 8) {
                        Position forwardTwo = new Position(newX, newY2);
                        if (board.getPieceAt(forwardTwo) == null) {
                            moves.add(forwardTwo);
                        }
                    }
                }
            }
        }

        char[] captureX = new char[2];
        captureX[0] = (char) (from.getX() - 1);
        captureX[1] = (char) (from.getX() + 1);
        for (char cx : captureX) {
            if (cx >= 'A' && cx <= 'H') {
                int captureY = from.getY() + direction;
                if (captureY >= 1 && captureY <= 8) {
                    Position capturePos = new Position(cx, captureY);
                    Piece p = board.getPieceAt(capturePos);
                    if (p != null && p.getColor() != piece.getColor()) {
                        moves.add(capturePos);
                    }
                }
            }
        }
        return moves;
    }
}

package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.RookMoveStrategy;

import java.awt.*;
import java.util.List;

public class Rook extends Piece {
    public Rook(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new RookMoveStrategy();
    }

    public boolean checkForCheck(Board board, Position kingPosition) {
        List<Position> possibleMoves = getPossibleMoves(board);
        for (Position pos : possibleMoves) {
            if (pos.equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }
    public char type() {
        return 'R';
    }
}

package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.QueenMoveStrategy;

import java.awt.*;
import java.util.List;

public class Queen extends Piece {
    public Queen(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new QueenMoveStrategy();
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
        return 'Q';
    }
}

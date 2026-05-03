package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.KnightMoveStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new KnightMoveStrategy();
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
        return 'N';
    }
}

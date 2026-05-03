package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.BishopMoveStrategy;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new BishopMoveStrategy();
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
        return 'B';
    }
}

package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.KingMoveStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new KingMoveStrategy();
    }

    public boolean checkForCheck(Board board, Position  kingPosition) {
        List<Position> possibleMoves = getPossibleMoves(board);
        for(Position pos : possibleMoves) {
            if(pos.equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }
    public char type() {
        return 'K';
    }
}

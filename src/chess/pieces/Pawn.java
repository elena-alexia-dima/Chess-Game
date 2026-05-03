package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.PawnMoveStrategy;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public Pawn(Colors color, Position position) {
        super(color, position);
        this.moveStrategy = new PawnMoveStrategy();
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.isFirstMove = firstMove;
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
        return 'P';
    }
}


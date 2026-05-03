package chess.pieces;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
import chess.strategy.MoveStrategy;

import java.util.*;

public abstract class Piece implements ChessPiece {
    private final Colors color;
    protected Position position;
    protected MoveStrategy moveStrategy;

    public Piece(Colors color, Position position) {
        this.color = color;
        this.position = position;
    }
    @Override
    public List<Position> getPossibleMoves(Board board) {
        return moveStrategy.getPossibleMoves(board, position);
    }
    public Colors getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}

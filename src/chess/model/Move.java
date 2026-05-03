package chess.model;

import chess.pieces.Piece;

import java.awt.*;

public class Move {
    private Colors playerColor;
    private Position from;
    private Position to;
    private Piece captured;

    public Move(Colors playerColor, Position from, Position to) {
        this.playerColor = playerColor;
        this.from = from;
        this.to = to;
        this.captured = null;
    }
    public Colors getPlayerColor() {
        return playerColor;
    }
    public Position getFrom() {
        return from;
    }
    public Position getTo() {
        return to;
    }
    public Piece getCaptured() {
        return captured;
    }
    public void setCaptured(Piece captured) {
        this.captured = captured;
    }

}

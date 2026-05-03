package chess.game;

import chess.board.Board;
import chess.exceptions.InvalidMoveException;
import chess.model.ChessPair;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;

public class Player {
    private String name;
    private Colors color;
    private List<Piece> capturedPieces;
    private TreeSet<ChessPair<Position, Piece>> ownedPieces;
    private int points;
    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
        this.ownedPieces = new TreeSet<>();
        this.points = 0;
    }
    public String getName() {
        return name;
    }
    public Colors getColor() {
        return color;
    }
    public void makeMove(Position from, Position to, Board board) throws InvalidMoveException {
        Piece moving = board.getPieceAt(from);
        if (moving == null) {
            throw new InvalidMoveException("There is no piece at" + from);
        }

        if (moving.getColor() != this.color) {
            throw new InvalidMoveException("Not your piece");
        }

        Piece captured = board.getPieceAt(to);
        board.movePiece(from, to);

        if (captured != null && captured.getColor() != this.color) {
            capturedPieces.add(captured);
    }
        updateOwnedPieces(board);

    }

    private void updateOwnedPieces(Board board) {
        ownedPieces.clear();
        for(char col = 'A'; col <= 'H'; col++) {
            for(int row = 1; row <= 8; row++) {
                Position pos = new Position(col, row);
                Piece piece = board.getPieceAt(pos);
                if(piece != null && piece.getColor() == this.color) {
                    ownedPieces.add(new ChessPair<>(pos, piece));
                }
            }
        }
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
    public List<ChessPair<Position, Piece>> getOwnedPieces() {
        List<ChessPair<Position, Piece>> result = new ArrayList<>();
        for(ChessPair<Position, Piece> p : this.ownedPieces) {
            result.add(p);
        }
        return result;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

package chess.factory;

import chess.model.Colors;
import chess.model.Position;
import chess.pieces.*;

public class PieceFactory {
    public static Piece createPiece(String type, Colors color, Position position) {
        switch(type) {

            case "K": return new King(color, position);
            case "Q": return new Queen(color, position);
            case "R": return new Rook(color, position);
            case "B": return new Bishop(color, position);
            case "N": return new Knight(color, position);
            case "P": return new Pawn(color, position);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
    public static Piece createPromotedPiece(String choice, Colors color, Position pos) {
        if (choice.equals("Q")) return new Queen(color, pos);
        if (choice.equals("R")) return new Rook(color, pos);
        if (choice.equals("B")) return new Bishop(color, pos);
        if (choice.equals("N")) return new Knight(color, pos);
        return new Queen(color, pos);
    }
}

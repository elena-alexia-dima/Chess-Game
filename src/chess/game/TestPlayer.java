package chess.game;
import chess.board.Board;
import chess.exceptions.InvalidMoveException;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Piece;
public class TestPlayer {
    public static void main(String[] args) {

        Player white = new Player("WhitePlayer", Colors.WHITE);
        Player black = new Player("BlackPlayer", Colors.BLACK);

        if (white.getColor() != Colors.WHITE || black.getColor() != Colors.BLACK) {
            System.out.println("Player color not set correctly");
        } else {
            System.out.println("Players created correctly");
        }

        Board board = new Board();
        board.initializare();

        try {
            Position from = new Position('A', 2);
            Position to = new Position('A', 3);

            white.makeMove(from, to, board);

            Piece moved = board.getPieceAt(to);
            if (moved == null || moved.getColor() != Colors.WHITE) {
                System.out.println("Piece not moved correctly");
            } else {
                System.out.println("Valid move executed successfully");
            }

        } catch (InvalidMoveException e) {
            System.out.println("Valid move thrown exception");
        }

        try {
            Position from = new Position('A', 7); // pion negru
            Position to = new Position('A', 6);

            white.makeMove(from, to, board);
            System.out.println("Illegal move allowed");

        } catch (InvalidMoveException e) {
            System.out.println("Illegal move correctly rejected");
        }

        try {
            black.makeMove(new Position('B', 7), new Position('B', 5), board);
            white.makeMove(new Position('A', 3), new Position('B', 4), board);
            black.makeMove(new Position('B', 5), new Position('B', 4), board);

            int before = black.getPoints();
            black.makeMove(new Position('B', 4), new Position('A', 3), board);

            if (black.getCapturedPieces().size() > 0 &&
                    black.getPoints() > before) {
                System.out.println("Capture handled correctly");
            } else {
                System.out.println("Capture not registered");
            }

        } catch (InvalidMoveException e) {
            System.out.println("Exception during capture test");
        }

        if (!white.getOwnedPieces().isEmpty()) {
            System.out.println("Owned pieces updated correctly");
        } else {
            System.out.println("Owned pieces empty");
        }

        System.out.println("White captured pieces: " + white.getCapturedPieces().size());
        System.out.println("White points: " + white.getPoints());
        System.out.println("Black points: " + black.getPoints());
    }
}

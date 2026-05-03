package chess.board;

import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Piece;

public class TestBoard {
    public static void main(String[] args) {
        Board board = new Board();
        board.initializare();

        System.out.println("Board before starting the game");
        printBoard(board);

        Position from = new Position('A', 2);
        Position to = new Position('A', 4);

        if (board.isValidMove(from, to)) {
            board.movePiece(from, to);
            System.out.println("\nAfter moving the white pawn from A2 to A4");
            printBoard(board);
        } else {
            System.out.println("Move from A2 to A4 is not valid");
        }

        Position fromKnight = new Position('B', 8);
        Position toKnight = new Position('C', 6);

        if (board.isValidMove(fromKnight, toKnight)) {
            board.movePiece(fromKnight, toKnight);
            System.out.println("\nAfter moving the black knight from B8 to C6");
            printBoard(board);
        } else {
            System.out.println("Move from B8 to C6 is not valid!");
        }

        Position pos1 = new Position('A', 2);
        Piece p1 = board.getPieceAt(pos1);
        if (p1 != null) {
            System.out.println("The piece at " + pos1 + " is: " + p1.type() + " " + p1.getColor());
        } else {
            System.out.println("There is no piece at " + pos1);
        }

        Position pos2 = new Position('A', 8);
        Piece p2 = board.getPieceAt(pos2);
        if (p2 != null) {
            System.out.println("The piece at " + pos2 + " is: " + p2.type() + " " + p2.getColor());
        } else {
            System.out.println("There is no piece at " + pos2);
        }

        Position Vfrom = new Position('A', 2);
        Position Vto = new Position('A', 4);
        if (board.isValidMove(Vfrom, Vto)) {
            System.out.println("Move from " + Vfrom + " to " + Vto + " is valid.");
            board.movePiece(Vfrom, Vto);
        } else {
            System.out.println("Move from " + Vfrom + " to " + Vto + " is not valid.");
        }

        System.out.println("\nAfter moving the white pawn from A2 to A4");
        printBoard(board);

        Position VfromKnight = new Position('B', 8);
        Position VtoKnight = new Position('C', 6);
        if (board.isValidMove(VfromKnight, VtoKnight)) {
            System.out.println("Move from " + VfromKnight + " to " + VtoKnight + " is valid.");
            board.movePiece(VfromKnight, VtoKnight);
        } else {
            System.out.println("Move from " + VfromKnight + " to " + VtoKnight + " is not valid.");
        }

        System.out.println("\nAfter moving the black knight from B8 to C6");
        printBoard(board);
    }

    public static void printBoard(Board board) {
        System.out.println("\n     A   B   C   D   E   F   G   H");
        System.out.println("  -------------------------------");

        for (int row = 8; row >= 1; row--) {
            System.out.print(row + " | ");
            for (char col = 'A'; col <= 'H'; col++) {
                Piece p = board.getPieceAt(new Position(col, row));
                if (p == null) {
                    System.out.print("... ");
                } else {
                    String color = (p.getColor() == Colors.WHITE) ? "W" : "B";
                    System.out.print(p.type() + "-" + color + " ");
                }
            }
            System.out.println("| " + row);
        }

        System.out.println("  -------------------------------");
        System.out.println("     A   B   C   D   E   F   G   H\n");
    }
}

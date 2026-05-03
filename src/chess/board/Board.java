package chess.board;

import chess.exceptions.InvalidMoveException;
import chess.factory.PieceFactory;
import chess.model.ChessPair;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.*;

import java.awt.*;
import java.util.TreeSet;
import java.util.List;

public class Board {
    private TreeSet<ChessPair<Position, Piece>> pieces;
    public Board() {
        pieces = new TreeSet<>();
    }
    public void initializare() {
        pieces.clear();

        pieces.add(new ChessPair<>(new Position('A', 1),
                PieceFactory.createPiece("R", Colors.WHITE, new Position('A', 1))));
        pieces.add(new ChessPair<>(new Position('B', 1),
                PieceFactory.createPiece("N", Colors.WHITE, new Position('B', 1))));
        pieces.add(new ChessPair<>(new Position('C', 1),
                PieceFactory.createPiece("B", Colors.WHITE, new Position('C', 1))));
        pieces.add(new ChessPair<>(new Position('D', 1),
                PieceFactory.createPiece("Q", Colors.WHITE, new Position('D', 1))));
        pieces.add(new ChessPair<>(new Position('E', 1),
                PieceFactory.createPiece("K", Colors.WHITE, new Position('E', 1))));
        pieces.add(new ChessPair<>(new Position('F', 1),
                PieceFactory.createPiece("B", Colors.WHITE, new Position('F', 1))));
        pieces.add(new ChessPair<>(new Position('G', 1),
                PieceFactory.createPiece("N", Colors.WHITE, new Position('G', 1))));
        pieces.add(new ChessPair<>(new Position('H', 1),
                PieceFactory.createPiece("R", Colors.WHITE, new Position('H', 1))));

        for (char c = 'A'; c <= 'H'; c++) {
            pieces.add(new ChessPair<>(new Position(c, 2),
                    PieceFactory.createPiece("P", Colors.WHITE, new Position(c, 2))));
        }

        for (char c = 'A'; c <= 'H'; c++) {
            pieces.add(new ChessPair<>(new Position(c, 7),
                    PieceFactory.createPiece("P", Colors.BLACK, new Position(c, 7))));
        }

        pieces.add(new ChessPair<>(new Position('A', 8),
                PieceFactory.createPiece("R", Colors.BLACK, new Position('A', 8))));
        pieces.add(new ChessPair<>(new Position('B', 8),
                PieceFactory.createPiece("N", Colors.BLACK, new Position('B', 8))));
        pieces.add(new ChessPair<>(new Position('C', 8),
                PieceFactory.createPiece("B", Colors.BLACK, new Position('C', 8))));
        pieces.add(new ChessPair<>(new Position('D', 8),
                PieceFactory.createPiece("Q", Colors.BLACK, new Position('D', 8))));
        pieces.add(new ChessPair<>(new Position('E', 8),
                PieceFactory.createPiece("K", Colors.BLACK, new Position('E', 8))));
        pieces.add(new ChessPair<>(new Position('F', 8),
                PieceFactory.createPiece("B", Colors.BLACK, new Position('F', 8))));
        pieces.add(new ChessPair<>(new Position('G', 8),
                PieceFactory.createPiece("N", Colors.BLACK, new Position('G', 8))));
        pieces.add(new ChessPair<>(new Position('H', 8),
                PieceFactory.createPiece("R", Colors.BLACK, new Position('H', 8))));
    }

    public Piece getPieceAt(Position position) {
        for(ChessPair<Position, Piece> chessPair : pieces) {
            if(chessPair.getKey().equals(position)) {
                return chessPair.getValue();
            }
        }
        return null;
    }

    public void movePiece(Position from, Position to) {
        if(!isValidMove(from,to))
            throw new InvalidMoveException("Invalid move");

        Piece moving = getPieceAt(from);
        Piece captured = getPieceAt(to);
        ChessPair<Position, Piece> old = null;
        for(ChessPair<Position, Piece> chessPair : pieces) {
            if(chessPair.getKey().equals(from)) {
                old = chessPair;
                break;
            }
        }
        if(old != null) {
            pieces.remove(old);
        }

        if(captured != null) {
            ChessPair<Position, Piece> newCap = null;
            for(ChessPair<Position, Piece> chessPair : pieces) {
                if(chessPair.getKey().equals(to)) {
                    newCap = chessPair;
                    break;
                }
            }
            if(newCap != null) {
                pieces.remove(newCap);
            }
        }

        moving.setPosition(to);
        pieces.add(new ChessPair<>(to, moving));

        if(moving instanceof Pawn) {
            ((Pawn)moving).setFirstMove(false);
        }
        if(moving.type() == 'P') {
            if((moving.getColor() == Colors.WHITE && to.getY() == 8) ||
                    (moving.getColor() == Colors.BLACK && to.getY() == 1)) {

                ChessPair<Position, Piece> pawnPair = null;
                for(ChessPair<Position, Piece> pair : pieces) {
                    if(pair.getKey().equals(to)) {
                        pawnPair = pair;
                        break;
                    }
                }
                pieces.remove(pawnPair);

                Piece promotedPiece = new Queen(moving.getColor(), to);
                pieces.add(new ChessPair<>(to, promotedPiece));
            }
        }
    }

    public boolean isValidMove(Position from, Position to) {
        if (to.getX() < 'A' || to.getX() > 'H' || to.getY() < 1 || to.getY() > 8) {
            return false;
        }
        Piece movingPiece = getPieceAt(from);
        if(movingPiece == null) {
            return false;
        }

        Piece targetPiece = getPieceAt(to);
        if(targetPiece != null && targetPiece.getColor() == movingPiece.getColor()) {
            return false;
        }

        // Check if move is in possible moves
        List<Position> possibleMoves = movingPiece.getPossibleMoves(this);
        boolean foundMove = false;
        for(Position p : possibleMoves) {
            if(p.equals(to)) {
                foundMove = true;
                break;
            }
        }

        if(!foundMove) {
            return false;
        }

        Colors playerColor = movingPiece.getColor();

        Piece temp = getPieceAt(to);
        ChessPair<Position, Piece> oldPair = null;
        for(ChessPair<Position, Piece> pair : pieces) {
            if(pair.getKey().equals(from)) {
                oldPair = pair;
            }
        }

        if(oldPair != null) pieces.remove(oldPair);

        ChessPair<Position, Piece> capPair = null;
        if(temp != null) {
            for(ChessPair<Position, Piece> pair : pieces) {
                if(pair.getKey().equals(to)) {
                    capPair = pair;
                }
            }
            if(capPair != null) pieces.remove(capPair);
        }

        movingPiece.setPosition(to);
        pieces.add(new ChessPair<>(to, movingPiece));

        Position kingPos = null;
        for(ChessPair<Position, Piece> pair : pieces) {
            Piece p = pair.getValue();
            if(p.type() == 'K' && p.getColor() == playerColor) {
                kingPos = p.getPosition();
            }
        }

        boolean inCheck = false;
        for(ChessPair<Position, Piece> pair : pieces) {
            Piece p = pair.getValue();
            if(p.getColor() != playerColor) {
                if(p.checkForCheck(this, kingPos)) {
                    inCheck = true;
                }
            }
        }

        pieces.remove(new ChessPair<>(to, movingPiece));
        movingPiece.setPosition(from);
        pieces.add(new ChessPair<>(from, movingPiece));
        if(temp != null) {
            pieces.add(new ChessPair<>(to, temp));
        }
        return !inCheck;
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

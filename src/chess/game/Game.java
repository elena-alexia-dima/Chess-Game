package chess.game;

import chess.board.Board;
import chess.model.Colors;
import chess.model.Move;
import chess.model.Position;
import chess.observer.GameObs;
import chess.pieces.Piece;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private Board board;
    private Player player1;
    private Player player2;
    private List<Move> moves;
    private int currentPlayer;
    private Points points;
    private List<GameObs> observers = new ArrayList<>();
    private List<String> boardHistory = new ArrayList<>();


    public Game(int id, Player player1, Player player2) {
        this.id = id;
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.moves = new ArrayList<>();
        this.currentPlayer = 0;
        this.points = new Points();
    }

    public int getId() {
        return id;
    }
    public Board getBoard() {
        return board;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public List<Move> getMoves() {
        return moves;
    }
    public Player getCurrentPlayer() {
        if(currentPlayer == 0){
            return player1;
        }
        return player2;
    }
    public Points getPoints() {
        return points;
    }
    public void start() {
        board.initializare();
        moves.clear();
        currentPlayer = 0;
    }
    public void resume() {

    }
    public void addObserver(GameObs observer) {
        observers.add(observer);
    }

    private void notifyObservers(String result, int gamePoints, int totalPoints) {
        for (GameObs o : observers) {
            o.gameEnded(result, gamePoints, totalPoints);
        }
    }

    public void switchPlayer() {
        if(currentPlayer == 0){
            currentPlayer = 1;
        } else {
            currentPlayer = 0;
        }
    }
    public boolean checkForCheckMate() {
        Player currentPlayer = getCurrentPlayer();
        Colors currentColor = currentPlayer.getColor();

        Position kingPos = null;
        for(char col = 'A'; col <= 'H'; col++) {
            for(int row = 1; row <= 8; row++) {
                Position pos = new Position(col, row);
                chess.pieces.Piece piece = board.getPieceAt(pos);
                if(piece != null && piece.type() == 'K' && piece.getColor() == currentColor) {
                    kingPos = pos;
                    break;
                }
            }
            if(kingPos != null) break;
        }

        if(kingPos == null) {
            return false;
        }

        boolean inCheck = false;
        for(char col = 'A'; col <= 'H'; col++) {
            for(int row = 1; row <= 8; row++) {
                Position pos = new Position(col, row);
                chess.pieces.Piece piece = board.getPieceAt(pos);
                if(piece != null && piece.getColor() != currentColor) {
                    if(piece.checkForCheck(board, kingPos)) {
                        inCheck = true;
                        break;
                    }
                }
            }
            if(inCheck) break;
        }

        if(!inCheck) {
            return false;
        }

        for(char col = 'A'; col <= 'H'; col++) {
            for(int row = 1; row <= 8; row++) {
                Position from = new Position(col, row);
                chess.pieces.Piece piece = board.getPieceAt(from);

                if(piece != null && piece.getColor() == currentColor) {
                    List<Position> possibleMoves = piece.getPossibleMoves(board);

                    for(Position to : possibleMoves) {
                        if(board.isValidMove(from, to)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void addMove(Player p, Position from, Position to) {
        Move move = new Move(p.getColor(), from, to);

        List<chess.pieces.Piece> captured = p.getCapturedPieces();
        if(!captured.isEmpty()) {
            chess.pieces.Piece capturedPiece = captured.get(captured.size() - 1);
            move.setCaptured(capturedPiece);
        }
        moves.add(move);
        if(move.getCaptured() != null) {
            points.onCapture(move.getCaptured());
        }
        boardHistory.add(boardState());
    }

    public void setCurrentPlayerIndex(int index) {
        this.currentPlayer = index;
    }

    public void endByCheckmate(Player winner, Player loser) {

        if (winner != null && loser != null) {
            points.onCheckmate(winner, loser);
        }

        notifyObservers(
                "CHECKMATE",
                points.getCurrentGameScore(),
                winner.getPoints()
        );

        points.reset();
    }

    public void endByResign(Player resigning, Player winner) {

        if (resigning != null && winner != null) {
            points.onResign(resigning, winner);
        }

        notifyObservers(
                "RESIGN",
                points.getCurrentGameScore(),
                winner.getPoints()
        );

        points.reset();
    }

    private String boardState() {
        StringBuilder sb = new StringBuilder();

        for (char c = 'A'; c <= 'H'; c++) {
            for (int r = 1; r <= 8; r++) {
                Piece p = board.getPieceAt(new Position(c, r));
                if (p == null) {
                    sb.append(".");
                } else {
                    sb.append(p.type()).append(p.getColor());
                }
            }
        }
        return sb.toString();
    }

    public boolean isDraw() {
        if (boardHistory.size() < 3) return false;

        int n = boardHistory.size();
        return boardHistory.get(n - 1).equals(boardHistory.get(n - 2)) &&
                boardHistory.get(n - 2).equals(boardHistory.get(n - 3));
    }

    public void endByDraw(Player human, Player computer) {

        if (human != null) {
            points.onDraw(human);
        }

        notifyObservers(
                "DRAW",
                points.getCurrentGameScore(),
                human.getPoints()
        );

        points.reset();
    }


}

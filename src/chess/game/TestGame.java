package chess.game;
import chess.board.Board;
import chess.model.Colors;
import chess.model.Position;
public class TestGame {
    public static void main(String[] args) {

        Player white = new Player("White", Colors.WHITE);
        Player black = new Player("Black", Colors.BLACK);

        Game game = new Game(1, white, black);

        if (game.getId() != 1) {
            System.out.println("Game ID incorrect");
        } else {
            System.out.println("Game ID set correctly");
        }

        game.start();

        Board board = game.getBoard();
        if (board == null) {
            System.out.println("Board not initialized");
        } else {
            System.out.println("Board initialized");
        }

        if (game.getCurrentPlayer() != white) {
            System.out.println("Initial player should be WHITE");
        } else {
            System.out.println("Initial player correct");
        }

        game.switchPlayer();
        if (game.getCurrentPlayer() != black) {
            System.out.println("Player switch failed");
        } else {
            System.out.println("Player switch works");
        }

        Position from = new Position('A', 2);
        Position to = new Position('A', 3);

        try {
            white.makeMove(from, to, board);
            game.addMove(white, from, to);

            if (game.getMoves().size() != 1) {
                System.out.println("Move not added");
            } else {
                System.out.println("Move added successfully");
            }

        } catch (Exception e) {
            System.out.println("Exception during move");
        }

        game.setCurrentPlayerIndex(0);
        if (game.getCurrentPlayer() != white) {
            System.out.println("setCurrentPlayerIndex failed");
        } else {
            System.out.println("setCurrentPlayerIndex works");
        }

        boolean mate = game.checkForCheckMate();
        System.out.println("Checkmate detected: " + mate + " (false)");

        System.out.println("Total moves stored: " + game.getMoves().size());

    }
}

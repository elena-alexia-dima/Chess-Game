package chess.main;

import chess.board.Board;
import chess.exceptions.InvalidCommandException;
import chess.exceptions.InvalidMoveException;
import chess.game.Game;
import chess.game.Player;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Piece;

import java.util.*;

public class Main {
    private static Main instance;
    private Scanner scanner;
    private Map<String, String> users;

    private Main() {
        scanner = new Scanner(System.in);
        users = new HashMap<>();
        setupUsers();
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    private void setupUsers() {
        users.put("player1", "pass1234");
    }

    private boolean authenticate() {
        System.out.println("=== Chess Game Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful!\n");
            return true;
        }

        System.out.println("Invalid username or password.\n");
        return false;
    }

    public void playGame() {

        while (!authenticate()) {
            System.out.println("Try again.\n");
        }

        Player computer = new Player("Computer", Colors.WHITE);
        Player human = new Player("You", Colors.BLACK);

        Game game = new Game(1, computer, human);
        game.start();

        boolean playing = true;

        while (playing) {
            try {
                game.getBoard().printBoard(game.getBoard());
                Player current = game.getCurrentPlayer();

                if (current.getColor() == Colors.WHITE) {

                    System.out.println("Computer is thinking...\n");
                    Thread.sleep(800);

                    makeComputerMove(game);

                    if (game.checkForCheckMate()) {
                        game.endByCheckmate(human, computer);
                        break;
                    }

                    if (game.isDraw()) {
                        game.endByDraw(human, computer);
                        break;
                    }

                    game.switchPlayer();
                    continue;
                }

                System.out.println("Your turn (Black)");
                System.out.print("Command (E2-E4 | SHOW E2 | RESIGN | EXIT): ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.equals("RESIGN")) {
                    Player resigning = game.getCurrentPlayer();
                    Player winner;

                    if (resigning == human) {
                        winner = computer;
                    } else {
                        winner = human;
                    }

                    game.endByResign(resigning, winner);
                    break;
                }

                if (input.equals("EXIT")) {
                    System.out.println("Game saved. Exiting...");
                    break;
                }

                if (input.startsWith("SHOW ")) {
                    Position pos = parsePosition(input.substring(5));
                    showPossibleMoves(game.getBoard(), pos, human);
                    continue;
                }

                if (input.matches("[A-H][1-8]-[A-H][1-8]")) {
                    Position from = parsePosition(input.substring(0, 2));
                    Position to = parsePosition(input.substring(3));

                    human.makeMove(from, to, game.getBoard());
                    game.addMove(human, from, to);

                    if (game.checkForCheckMate()) {
                        game.endByCheckmate(human, computer);
                        break;
                    }

                    if (game.isDraw()) {
                        game.endByDraw(human, computer);
                        break;
                    }

                    game.switchPlayer();
                    continue;
                }

                throw new InvalidCommandException("Invalid command!");

            } catch (InvalidMoveException e) {
                System.out.println("Invalid move: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }

        System.out.println("\n=== GAME OVER ===");
        System.out.println("Computer points: " + computer.getPoints());
        System.out.println("Your points: " + human.getPoints());
    }


    private void makeComputerMove(Game game) throws InvalidMoveException {

        Player computer = game.getCurrentPlayer();
        Board board = game.getBoard();

        List<Move> validMoves = new ArrayList<>();

        for (char c = 'A'; c <= 'H'; c++) {
            for (int r = 1; r <= 8; r++) {
                Position from = new Position(c, r);
                Piece p = board.getPieceAt(from);

                if (p != null && p.getColor() == Colors.WHITE) {
                    for (Position to : p.getPossibleMoves(board)) {
                        if (board.isValidMove(from, to)) {
                            validMoves.add(new Move(from, to));
                        }
                    }
                }
            }
        }

        if (validMoves.isEmpty()) {
            System.out.println("Computer has no valid moves!");
            return;
        }

        Move m = validMoves.get(new Random().nextInt(validMoves.size()));
        computer.makeMove(m.from, m.to, board);
        game.addMove(computer, m.from, m.to);

        System.out.println("Computer moved: " + m.from + "-" + m.to + "\n");
    }

    private void showPossibleMoves(Board board, Position pos, Player player) {
        Piece p = board.getPieceAt(pos);

        if (p == null || p.getColor() != player.getColor()) {
            System.out.println("Invalid piece.\n");
            return;
        }

        System.out.print("Moves: ");
        for (Position move : p.getPossibleMoves(board)) {
            if (board.isValidMove(pos, move)) {
                System.out.print(move + " ");
            }
        }
        System.out.println("\n");
    }

    private Position parsePosition(String s) {
        return new Position(s.charAt(0), Integer.parseInt(s.substring(1)));
    }

    private static class Move {
        Position from, to;

        Move(Position f, Position t) {
            from = f;
            to = t;
        }
    }

    public static void main(String[] args) {
        Main.getInstance().playGame();
    }
}



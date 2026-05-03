package chess.user;
import chess.game.Game;
import chess.game.Player;
import chess.model.Colors;

public class TestUser {
    public static void main(String[] args) {

        User u = new User("test@example.com", "pass123");

        if (!u.getEmail().equals("test@example.com")) {
            System.out.println("Email not set correctly");
        }
        if (!u.getPassword().equals("pass123")) {
            System.out.println("Password not set correctly");
        }
        if (u.getPoints() != 0) {
            System.out.println("Initial points should be 0");
        }

        System.out.println("User created successfully.");

        Player p1 = new Player("You", Colors.WHITE);
        Player p2 = new Player("Computer", Colors.BLACK);

        Game g1 = new Game(1, p1, p2);
        Game g2 = new Game(2, p1, p2);

        u.addGame(g1);
        u.addGame(g2);

        if (u.getActiveGames().size() != 2) {
            System.out.println("Games not added correctly");
        } else {
            System.out.println("Games added successfully.");
        }

        u.removeGame(g1);
        if (u.getActiveGames().size() != 1) {
            System.out.println("Game not removed correctly");
        } else {
            System.out.println("Game removed successfully.");
        }

        u.setPoints(200);
        if (u.getPoints() != 200) {
            System.out.println("Points not updated correctly");
        } else {
            System.out.println("Points updated successfully.");
        }

        System.out.println("Final points: " + u.getPoints());
        System.out.println("Active games count: " + u.getActiveGames().size());
    }
}

package chess.observer;

public class ConsoleObs implements GameObs {
    @Override
    public void gameEnded(String result, int gamePoints, int totalPoints) {
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Result: " + result);
        System.out.println("Points this game: " + gamePoints);
        System.out.println("Total points: " + totalPoints);
    }
}

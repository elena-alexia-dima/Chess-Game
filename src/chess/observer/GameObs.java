package chess.observer;

public interface GameObs {
    void gameEnded(String result, int gamePoints, int totalPoints);
}

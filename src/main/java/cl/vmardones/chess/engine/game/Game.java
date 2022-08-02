package cl.vmardones.chess.engine.game;

public class Game {

    private final GameState gameState;
    private final GameHistory gameHistory;

    public Game() {
        gameState = new GameState();
        gameHistory = new GameHistory();
    }
}

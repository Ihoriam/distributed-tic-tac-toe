package org.ihor.gameengine;

import org.ihor.gameengine.exceptions.GameNotFoundException;
import org.ihor.gameengine.models.Game;
import org.ihor.gameengine.models.MoveResult;
import org.ihor.gameengine.models.Player;

import java.util.concurrent.atomic.AtomicLong;

public class GameService {

    private final GameRepository gameRepository;
    private final AtomicLong idCounter = new AtomicLong(1);

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame() {
        String gameId = String.valueOf(idCounter.getAndIncrement());
        Game game = new Game(gameId);
        gameRepository.save(game);
        return game;
    }

    public Game getGame(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found: " + gameId));
    }

    public Game makeMove(String gameId, int row, int col, Player player) {
        Game game = getGame(gameId);
        MoveResult result = game.makeMove(row, col, player);
        gameRepository.save(game);
        return game;
    }
}

package org.ihor.gameengine;

import org.ihor.gameengine.exceptions.GameNotFoundException;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String gameId) {
        if (gameRepository.findById(gameId).isPresent()) {
            throw new IllegalArgumentException("Game already exists: " + gameId);
        }
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
        game.makeMove(row, col, player);
        gameRepository.save(game);
        return game;
    }
}

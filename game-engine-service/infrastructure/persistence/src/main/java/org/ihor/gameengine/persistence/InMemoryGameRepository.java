package org.ihor.gameengine.persistence;

import org.ihor.gameengine.Game;
import org.ihor.gameengine.GameRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGameRepository implements GameRepository {

    private final ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        games.put(game.getGameId(), game);
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(games.get(gameId));
    }
}

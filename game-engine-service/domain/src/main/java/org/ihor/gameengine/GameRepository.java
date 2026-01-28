package org.ihor.gameengine;

import org.ihor.gameengine.models.Game;

import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<Game> findById(String gameId);
}

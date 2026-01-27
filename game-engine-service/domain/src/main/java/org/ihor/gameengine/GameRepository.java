package org.ihor.gameengine;

import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<Game> findById(String gameId);
}

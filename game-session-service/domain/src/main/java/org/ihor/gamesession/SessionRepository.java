package org.ihor.gamesession;

import java.util.Optional;

public interface SessionRepository {
    void save(Session session);
    Optional<Session> findById(String sessionId);
}

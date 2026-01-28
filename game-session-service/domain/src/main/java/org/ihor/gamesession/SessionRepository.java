package org.ihor.gamesession;

import org.ihor.gamesession.models.Session;

import java.util.Optional;

public interface SessionRepository {
    void save(Session session);
    Optional<Session> findById(String sessionId);
}

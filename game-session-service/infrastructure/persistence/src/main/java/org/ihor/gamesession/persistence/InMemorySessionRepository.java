package org.ihor.gamesession.persistence;

import org.ihor.gamesession.SessionRepository;
import org.ihor.gamesession.models.Session;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionRepository implements SessionRepository {

    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @Override
    public void save(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    @Override
    public Optional<Session> findById(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }
}

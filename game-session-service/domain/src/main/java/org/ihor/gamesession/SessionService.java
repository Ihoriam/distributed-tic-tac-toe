package org.ihor.gamesession;

import org.ihor.gamesession.exceptions.SessionNotFoundException;

import java.time.Instant;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final GameEngineGateway gameEngineGateway;
    private final RandomMoveGenerator moveGenerator;

    public SessionService(SessionRepository sessionRepository,
                          GameEngineGateway gameEngineGateway,
                          RandomMoveGenerator moveGenerator) {
        this.sessionRepository = sessionRepository;
        this.gameEngineGateway = gameEngineGateway;
        this.moveGenerator = moveGenerator;
    }

    public Session createSession(String sessionId) {
        if (sessionRepository.findById(sessionId).isPresent()) {
            throw new IllegalArgumentException("Session already exists: " + sessionId);
        }
        GameState gameState = gameEngineGateway.createGame(sessionId);
        Session session = new Session(sessionId, sessionId);
        session.updateGameState(gameState);
        sessionRepository.save(session);
        return session;
    }

    public Session getSession(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found: " + sessionId));
    }

    public Session simulateGame(String sessionId) {
        Session session = getSession(sessionId);
        if (session.isCompleted()) {
            throw new IllegalStateException("Session is already completed: " + sessionId);
        }

        GameState state = session.getCurrentGameState();
        while ("PLAYING".equals(state.status())) {
            int[] cell = moveGenerator.pickMove(state.board());
            String player = state.currentTurn();

            state = gameEngineGateway.makeMove(sessionId, cell[0], cell[1], player);

            Move move = new Move(cell[0], cell[1], player, Instant.now());
            session.addMove(move);
            session.updateGameState(state);
        }

        sessionRepository.save(session);
        return session;
    }
}

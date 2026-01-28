package org.ihor.gamesession;

import org.ihor.gamesession.exceptions.SessionNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final GameEngineGateway gameEngineGateway;
    private final RandomMoveGenerator moveGenerator;

    private final AtomicLong idCounter = new AtomicLong(1);

    public SessionService(SessionRepository sessionRepository,
                          GameEngineGateway gameEngineGateway,
                          RandomMoveGenerator moveGenerator) {
        this.sessionRepository = sessionRepository;
        this.gameEngineGateway = gameEngineGateway;
        this.moveGenerator = moveGenerator;
    }

    public Session createSession() {
        String sessionId = String.valueOf(idCounter.getAndIncrement());
        GameState gameState = gameEngineGateway.createGame();
        Session session = new Session(sessionId);
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

        String gameId = session.getGameId();
        GameState state = session.getCurrentGameState();
        while (state.status() == SessionGameStatus.PLAYING) {
            int[] cell = moveGenerator.pickMove(state.board());
            String player = state.currentTurn();

            state = gameEngineGateway.makeMove(gameId, cell[0], cell[1], player);

            Move move = new Move(cell[0], cell[1], player);
            session.addMove(move);
            session.updateGameState(state);
        }

        sessionRepository.save(session);
        return session;
    }
}

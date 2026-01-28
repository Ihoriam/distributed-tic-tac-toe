package org.ihor.gamesession;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {

    private final String sessionId;
    private final String gameId;
    private final List<Move> moveHistory;
    private GameState currentGameState;
    private final Instant createdAt;
    private Instant completedAt;

    public Session(String sessionId, String gameId) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.moveHistory = new ArrayList<>();
        this.createdAt = Instant.now();
    }

    public void addMove(Move move) {
        moveHistory.add(move);
    }

    public void updateGameState(GameState gameState) {
        this.currentGameState = gameState;
        if (!"PLAYING".equals(gameState.status())) {
            this.completedAt = Instant.now();
        }
    }

    public boolean isCompleted() {
        return completedAt != null;
    }

    public String getSessionId() { return sessionId; }
    public String getGameId() { return gameId; }
    public List<Move> getMoveHistory() { return Collections.unmodifiableList(moveHistory); }
    public GameState getCurrentGameState() { return currentGameState; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getCompletedAt() { return completedAt; }
}

package org.ihor.gamesession.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Session {

    private final String sessionId;
    private final LinkedList<Move> moveHistory;
    private GameState currentGameState;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.moveHistory = new LinkedList<>();
    }

    public void addMove(Move move) {
        moveHistory.add(move);
    }

    public void updateGameState(GameState gameState) {
        this.currentGameState = gameState;
    }

    public boolean isCompleted() {
        return currentGameState != null && currentGameState.status() != SessionGameStatus.PLAYING;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getGameId() {
        return currentGameState != null ? currentGameState.gameId() : null;
    }

    public List<Move> getMoveHistory() {
        return Collections.unmodifiableList(moveHistory);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}

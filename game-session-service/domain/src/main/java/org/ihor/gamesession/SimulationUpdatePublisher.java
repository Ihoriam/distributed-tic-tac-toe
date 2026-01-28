package org.ihor.gamesession;

import org.ihor.gamesession.models.GameState;
import org.ihor.gamesession.models.Move;

public interface SimulationUpdatePublisher {
    void publishMoveUpdate(String sessionId, Move move, GameState state, int moveNumber);

    void publishSimulationComplete(String sessionId, GameState finalState, int totalMoves);
}

package org.ihor.gamesession;

public interface SimulationUpdatePublisher {
    void publishMoveUpdate(String sessionId, Move move, GameState state, int moveNumber);

    void publishSimulationComplete(String sessionId, GameState finalState, int totalMoves);
}

package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.Session;

public record SimulationResponse(String sessionId, String finalStatus, int totalMoves, String message) {

    public static SimulationResponse from(Session session) {
        String status = session.getCurrentGameState().status();
        String message = switch (status) {
            case "X_WON" -> "Player X wins!";
            case "O_WON" -> "Player O wins!";
            case "DRAW" -> "Game ended in a draw.";
            default -> "Game over.";
        };
        return new SimulationResponse(session.getSessionId(), status, session.getMoveHistory().size(), message);
    }
}

package org.ihor.gamesession.websocket.dto;

public record MoveUpdateMessage(
    String sessionId,
    int row,
    int col,
    String player,
    String[] board,
    String status,
    int moveNumber,
    boolean isComplete,
    String message
) {
}

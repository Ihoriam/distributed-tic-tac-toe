package org.ihor.gamesession;

public record GameState(String gameId, String[] board, SessionPlayer currentTurn, SessionGameStatus status) {
}

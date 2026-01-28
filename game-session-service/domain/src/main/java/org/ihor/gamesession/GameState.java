package org.ihor.gamesession;

public record GameState(String gameId, String[] board, String currentTurn, SessionGameStatus status) {
}

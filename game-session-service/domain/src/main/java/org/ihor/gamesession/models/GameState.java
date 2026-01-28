package org.ihor.gamesession.models;

public record GameState(String gameId, String[] board, SessionPlayer currentTurn, SessionGameStatus status) {
}

package org.ihor.gamesession.gameengine;

public record GameEngineResponse(String gameId, String[] board, String currentTurn, String status) {
}

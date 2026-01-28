package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.models.GameState;

public record GameStateDto(String gameId, String[] board, String currentTurn, String status) {

    public static GameStateDto from(GameState gameState) {
        if (gameState == null) return null;
        return new GameStateDto(gameState.gameId(), gameState.board(), gameState.currentTurn().name(), gameState.status().name());
    }
}

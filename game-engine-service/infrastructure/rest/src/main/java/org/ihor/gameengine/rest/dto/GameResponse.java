package org.ihor.gameengine.rest.dto;

import org.ihor.gameengine.Game;
import org.ihor.gameengine.Player;

public record GameResponse(String gameId, String[] board, String currentTurn, String status) {

    public static GameResponse from(Game game) {
        Player[] cells = game.getBoard().getCells();
        String[] board = new String[9];
        for (int i = 0; i < 9; i++) {
            board[i] = cells[i] == null ? null : cells[i].name();
        }
        return new GameResponse(
                game.getGameId(),
                board,
                game.getCurrentTurn().name(),
                game.getStatus().name()
        );
    }
}

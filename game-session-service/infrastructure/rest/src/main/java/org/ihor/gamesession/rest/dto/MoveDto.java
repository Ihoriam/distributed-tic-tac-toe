package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.Move;

import java.time.Instant;

public record MoveDto(int row, int col, String player, Instant timestamp) {

    public static MoveDto from(Move move) {
        return new MoveDto(move.row(), move.col(), move.player(), move.timestamp());
    }
}

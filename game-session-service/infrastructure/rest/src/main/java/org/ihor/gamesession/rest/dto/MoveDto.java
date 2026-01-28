package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.Move;

public record MoveDto(int row, int col, String player) {

    public static MoveDto from(Move move) {
        return new MoveDto(move.row(), move.col(), move.player());
    }
}

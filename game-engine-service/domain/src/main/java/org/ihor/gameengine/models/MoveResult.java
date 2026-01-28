package org.ihor.gameengine.models;

public record MoveResult(Player player, int row, int col, GameStatus resultingStatus) {
}

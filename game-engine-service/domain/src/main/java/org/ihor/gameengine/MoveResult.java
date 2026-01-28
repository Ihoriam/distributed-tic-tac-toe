package org.ihor.gameengine;

public record MoveResult(Player player, int row, int col, GameStatus resultingStatus) {
}

package org.ihor.gamesession;

import java.time.Instant;

public record Move(int row, int col, String player, Instant timestamp) {
}

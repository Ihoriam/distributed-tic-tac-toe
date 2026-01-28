package org.ihor.gamesession;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMoveGenerator {

    private final Random random;

    public RandomMoveGenerator(Random random) {
        this.random = random;
    }

    public int[] pickMove(String[] board) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == null) {
                emptyCells.add(new int[]{i / 3, i % 3});
            }
        }
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
}

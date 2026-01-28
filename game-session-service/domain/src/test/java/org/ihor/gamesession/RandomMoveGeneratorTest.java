package org.ihor.gamesession;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomMoveGeneratorTest {

    @Test
    void picksValidEmptyCell() {
        RandomMoveGenerator gen = new RandomMoveGenerator(new Random(42));
        String[] board = new String[9]; // all null = all empty

        int[] move = gen.pickMove(board);

        assertTrue(move[0] >= 0 && move[0] <= 2);
        assertTrue(move[1] >= 0 && move[1] <= 2);
    }

    @Test
    void picksSingleEmptyCell() {
        RandomMoveGenerator gen = new RandomMoveGenerator(new Random(0));
        String[] board = {"X", "O", "X", "O", "X", "O", "X", "O", null};

        int[] move = gen.pickMove(board);

        assertEquals(2, move[0]);
        assertEquals(2, move[1]);
    }

    @Test
    void seededRandomProducesDeterministicResult() {
        String[] board = new String[9];

        int[] move1 = new RandomMoveGenerator(new Random(123)).pickMove(board);
        int[] move2 = new RandomMoveGenerator(new Random(123)).pickMove(board);

        assertArrayEquals(move1, move2);
    }
}

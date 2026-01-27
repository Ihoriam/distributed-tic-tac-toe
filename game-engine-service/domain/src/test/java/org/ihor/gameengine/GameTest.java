package org.ihor.gameengine;

import org.ihor.gameengine.exceptions.GameOverException;
import org.ihor.gameengine.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game("test-game");
    }

    @Test
    void newGameHasCorrectInitialState() {
        assertEquals("test-game", game.getGameId());
        assertEquals(Player.X, game.getCurrentTurn());
        assertEquals(GameStatus.PLAYING, game.getStatus());

        Player[] cells = game.getBoard().getCells();
        for (Player cell : cells) {
            assertNull(cell);
        }
    }

    @Test
    void makeMoveUpdatesBoard() {
        game.makeMove(0, 0, Player.X);

        assertEquals(Player.X, game.getBoard().getCell(0, 0));
    }

    @Test
    void makeMoveAlternatesTurn() {
        game.makeMove(0, 0, Player.X);
        assertEquals(Player.O, game.getCurrentTurn());

        game.makeMove(1, 0, Player.O);
        assertEquals(Player.X, game.getCurrentTurn());
    }

    @Test
    void xWinsWithTopRow() {
        game.makeMove(0, 0, Player.X);
        game.makeMove(1, 0, Player.O);
        game.makeMove(0, 1, Player.X);
        game.makeMove(1, 1, Player.O);
        game.makeMove(0, 2, Player.X);

        assertEquals(GameStatus.X_WON, game.getStatus());
    }

    @Test
    void oWinsWithColumn() {
        game.makeMove(0, 0, Player.X);
        game.makeMove(0, 1, Player.O);
        game.makeMove(1, 0, Player.X);
        game.makeMove(1, 1, Player.O);
        game.makeMove(2, 2, Player.X);
        game.makeMove(2, 1, Player.O);

        assertEquals(GameStatus.O_WON, game.getStatus());
    }

    @Test
    void xWinsWithDiagonal() {
        game.makeMove(0, 0, Player.X);
        game.makeMove(0, 1, Player.O);
        game.makeMove(1, 1, Player.X);
        game.makeMove(0, 2, Player.O);
        game.makeMove(2, 2, Player.X);

        assertEquals(GameStatus.X_WON, game.getStatus());
    }

    @Test
    void xWinsWithAntiDiagonal() {
        game.makeMove(0, 2, Player.X);
        game.makeMove(0, 0, Player.O);
        game.makeMove(1, 1, Player.X);
        game.makeMove(1, 0, Player.O);
        game.makeMove(2, 0, Player.X);

        assertEquals(GameStatus.X_WON, game.getStatus());
    }

    @Test
    void drawWhenBoardFullWithNoWinner() {
        // X O X
        // X X O
        // O X O
        game.makeMove(0, 0, Player.X);
        game.makeMove(0, 1, Player.O);
        game.makeMove(0, 2, Player.X);
        game.makeMove(1, 2, Player.O);
        game.makeMove(1, 0, Player.X);
        game.makeMove(2, 0, Player.O);
        game.makeMove(1, 1, Player.X);
        game.makeMove(2, 2, Player.O);
        game.makeMove(2, 1, Player.X);

        assertEquals(GameStatus.DRAW, game.getStatus());
    }

    @Test
    void throwsOnOutOfBoundsMove() {
        assertThrows(InvalidMoveException.class, () -> game.makeMove(-1, 0, Player.X));
        assertThrows(InvalidMoveException.class, () -> game.makeMove(0, 3, Player.X));
        assertThrows(InvalidMoveException.class, () -> game.makeMove(3, 0, Player.X));
        assertThrows(InvalidMoveException.class, () -> game.makeMove(0, -1, Player.X));
    }

    @Test
    void throwsOnWrongTurn() {
        assertThrows(InvalidMoveException.class, () -> game.makeMove(0, 0, Player.O));
    }

    @Test
    void throwsOnOccupiedCell() {
        game.makeMove(0, 0, Player.X);

        assertThrows(InvalidMoveException.class, () -> game.makeMove(0, 0, Player.O));
    }

    @Test
    void throwsOnMoveAfterGameOver() {
        game.makeMove(0, 0, Player.X);
        game.makeMove(1, 0, Player.O);
        game.makeMove(0, 1, Player.X);
        game.makeMove(1, 1, Player.O);
        game.makeMove(0, 2, Player.X); // X wins

        assertThrows(GameOverException.class, () -> game.makeMove(2, 0, Player.O));
    }
}

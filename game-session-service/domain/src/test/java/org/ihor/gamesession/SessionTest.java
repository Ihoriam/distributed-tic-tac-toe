package org.ihor.gamesession;

import org.ihor.gamesession.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session("s1");
    }

    @Test
    void initialState() {
        assertEquals("s1", session.getSessionId());
        assertNull(session.getGameId());
        assertNull(session.getCurrentGameState());
        assertTrue(session.getMoveHistory().isEmpty());
        assertFalse(session.isCompleted());
    }

    @Test
    void addMoveAppendsToHistory() {
        session.addMove(new Move(0, 0, SessionPlayer.X));
        session.addMove(new Move(1, 1, SessionPlayer.O));

        assertEquals(2, session.getMoveHistory().size());
        assertEquals(SessionPlayer.X, session.getMoveHistory().get(0).player());
        assertEquals(SessionPlayer.O, session.getMoveHistory().get(1).player());
    }

    @Test
    void updateGameStateSetsState() {
        GameState state = new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.PLAYING);
        session.updateGameState(state);

        assertEquals("g1", session.getGameId());
        assertSame(state, session.getCurrentGameState());
    }

    @Test
    void isCompletedReturnsTrueWhenGameOver() {
        session.updateGameState(new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.X_WON));
        assertTrue(session.isCompleted());
    }

    @Test
    void isCompletedReturnsFalseWhenPlaying() {
        session.updateGameState(new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.PLAYING));
        assertFalse(session.isCompleted());
    }

    @Test
    void moveHistoryIsUnmodifiable() {
        session.addMove(new Move(0, 0, SessionPlayer.X));
        assertThrows(UnsupportedOperationException.class, () -> session.getMoveHistory().add(new Move(1, 1, SessionPlayer.O)));
    }
}

package org.ihor.gamesession;

import org.ihor.gamesession.exceptions.SessionNotFoundException;
import org.ihor.gamesession.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private GameEngineGateway gameEngineGateway;

    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        RandomMoveGenerator moveGenerator = new RandomMoveGenerator(new Random(42));
        sessionService = new SessionService(sessionRepository, gameEngineGateway, moveGenerator);
    }

    @Test
    void createSessionCreatesGameAndSaves() {
        GameState initialState = new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.PLAYING);
        when(gameEngineGateway.createGame()).thenReturn(initialState);

        Session session = sessionService.createSession();

        assertEquals("1", session.getSessionId());
        assertEquals("g1", session.getGameId());
        assertFalse(session.isCompleted());
        verify(sessionRepository).save(session);
    }

    @Test
    void getSessionReturnsExisting() {
        Session existing = new Session("s1");
        when(sessionRepository.findById("s1")).thenReturn(Optional.of(existing));

        Session session = sessionService.getSession("s1");

        assertSame(existing, session);
    }

    @Test
    void getSessionThrowsWhenNotFound() {
        when(sessionRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(SessionNotFoundException.class, () -> sessionService.getSession("missing"));
    }

    @Test
    void simulateGameRunsToCompletion() {
        Session session = new Session("s1");
        GameState playing = new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.PLAYING);
        session.updateGameState(playing);
        when(sessionRepository.findById("s1")).thenReturn(Optional.of(session));

        // Return X_WON immediately on first move
        GameState won = new GameState("g1", new String[]{"X", null, null, null, null, null, null, null, null}, SessionPlayer.O, SessionGameStatus.X_WON);
        when(gameEngineGateway.makeMove(eq("g1"), anyInt(), anyInt(), any(SessionPlayer.class))).thenReturn(won);

        Session result = sessionService.simulateGame("s1");

        assertTrue(result.isCompleted());
        assertEquals(1, result.getMoveHistory().size());
        verify(sessionRepository).save(session);
    }

    @Test
    void simulateGameOnCompletedSessionThrows() {
        Session session = new Session("s1");
        session.updateGameState(new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.X_WON));
        when(sessionRepository.findById("s1")).thenReturn(Optional.of(session));

        assertThrows(IllegalStateException.class, () -> sessionService.simulateGame("s1"));
    }

    @Test
    void simulateGameWithSpeedPublishesUpdates() {
        Session session = new Session("s1");
        GameState playing = new GameState("g1", new String[9], SessionPlayer.X, SessionGameStatus.PLAYING);
        session.updateGameState(playing);
        when(sessionRepository.findById("s1")).thenReturn(Optional.of(session));

        GameState won = new GameState("g1", new String[]{"X", null, null, null, null, null, null, null, null}, SessionPlayer.O, SessionGameStatus.X_WON);
        when(gameEngineGateway.makeMove(eq("g1"), anyInt(), anyInt(), any(SessionPlayer.class))).thenReturn(won);

        SimulationUpdatePublisher publisher = mock(SimulationUpdatePublisher.class);

        sessionService.simulateGameWithSpeed("s1", 0, publisher);

        verify(publisher).publishMoveUpdate(eq("s1"), any(Move.class), eq(won), eq(1));
        verify(publisher).publishSimulationComplete("s1", won, 1);
    }
}

package org.ihor.gameengine;

import org.ihor.gameengine.exceptions.GameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(gameRepository);
    }

    @Test
    void createGameSavesAndReturnsNewGameWithSequentialId() {
        Game game1 = gameService.createGame();
        Game game2 = gameService.createGame();

        assertEquals("1", game1.getGameId());
        assertEquals("2", game2.getGameId());
        assertEquals(GameStatus.PLAYING, game1.getStatus());
        assertEquals(Player.X, game1.getCurrentTurn());
        verify(gameRepository, times(2)).save(any(Game.class));
    }

    @Test
    void getGameReturnsExistingGame() {
        Game existing = new Game("game1");
        when(gameRepository.findById("game1")).thenReturn(Optional.of(existing));

        Game game = gameService.getGame("game1");

        assertSame(existing, game);
    }

    @Test
    void getGameThrowsForUnknownId() {
        when(gameRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getGame("nonexistent"));
    }

    @Test
    void makeMoveUpdatesAndSavesGame() {
        Game existing = new Game("game1");
        when(gameRepository.findById("game1")).thenReturn(Optional.of(existing));

        Game game = gameService.makeMove("game1", 0, 0, Player.X);

        assertEquals(Player.X, game.getBoard().getCell(0, 0));
        assertEquals(Player.O, game.getCurrentTurn());
        verify(gameRepository).save(game);
    }

    @Test
    void makeMoveOnUnknownGameThrows() {
        when(gameRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class,
                () -> gameService.makeMove("nonexistent", 0, 0, Player.X));
        verify(gameRepository, never()).save(any());
    }

    @Test
    void fullGameFlowToWin() {
        Game existing = new Game("game1");
        when(gameRepository.findById("game1")).thenReturn(Optional.of(existing));

        gameService.makeMove("game1", 0, 0, Player.X);
        gameService.makeMove("game1", 1, 0, Player.O);
        gameService.makeMove("game1", 0, 1, Player.X);
        gameService.makeMove("game1", 1, 1, Player.O);
        Game game = gameService.makeMove("game1", 0, 2, Player.X);

        assertEquals(GameStatus.X_WON, game.getStatus());
        verify(gameRepository, times(5)).save(game);
    }
}

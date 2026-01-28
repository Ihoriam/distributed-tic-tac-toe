package org.ihor.gameengine.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.ihor.gameengine.Game;
import org.ihor.gameengine.GameService;
import org.ihor.gameengine.Player;
import org.ihor.gameengine.rest.dto.GameResponse;
import org.ihor.gameengine.rest.dto.MoveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new game", description = "Creates a new game with an auto-generated sequential ID")
    public GameResponse createGame() {
        return GameResponse.from(gameService.createGame());
    }

    @PostMapping("/{gameId}/move")
    @Operation(summary = "Make a move", description = "Make a move in the specified game")
    public GameResponse makeMove(
        @PathVariable("gameId") String gameId,
        @RequestBody MoveRequest request) {
        Player player = Player.valueOf(request.player());
        Game game = gameService.makeMove(gameId, request.row(), request.col(), player);
        return GameResponse.from(game);
    }

    @GetMapping("/{gameId}")
    @Operation(summary = "Get game state", description = "Retrieve the current state of a game")
    public GameResponse getGame(
        @PathVariable("gameId") String gameId) {
        return GameResponse.from(gameService.getGame(gameId));
    }
}

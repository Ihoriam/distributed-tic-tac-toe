package org.ihor.gameengine.rest;

import org.ihor.gameengine.GameService;
import org.ihor.gameengine.Player;
import org.ihor.gameengine.Game;
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

    @PostMapping("/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse createGame(@PathVariable String gameId) {
        return GameResponse.from(gameService.createGame(gameId));
    }

    @PostMapping("/{gameId}/move")
    public GameResponse makeMove(@PathVariable String gameId, @RequestBody MoveRequest request) {
        Player player = Player.valueOf(request.player());
        Game game = gameService.makeMove(gameId, request.row(), request.col(), player);
        return GameResponse.from(game);
    }

    @GetMapping("/{gameId}")
    public GameResponse getGame(@PathVariable String gameId) {
        return GameResponse.from(gameService.getGame(gameId));
    }
}

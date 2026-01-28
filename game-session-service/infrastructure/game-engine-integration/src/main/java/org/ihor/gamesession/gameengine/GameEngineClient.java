package org.ihor.gamesession.gameengine;

import org.ihor.gamesession.GameEngineGateway;
import org.ihor.gamesession.GameState;
import org.ihor.gamesession.SessionPlayer;
import org.ihor.gamesession.exceptions.GameEngineException;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class GameEngineClient implements GameEngineGateway {

    private final RestClient restClient;

    public GameEngineClient(String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GameState createGame() {
        try {
            GameEngineResponse response = restClient.post()
                .uri("/games")
                    .retrieve()
                .body(GameEngineResponse.class);
            return toGameState(response);
        } catch (Exception e) {
            throw new GameEngineException("Failed to create game", e);
        }
    }

    @Override
    public GameState makeMove(String gameId, int row, int col, SessionPlayer player) {
        try {
            GameEngineResponse response = restClient.post()
                    .uri("/games/{gameId}/move", gameId)
                .body(Map.of("row", row, "col", col, "player", player.name()))
                    .retrieve()
                .body(GameEngineResponse.class);
            return toGameState(response);
        } catch (Exception e) {
            throw new GameEngineException("Failed to make move for game: " + gameId, e);
        }
    }

    @Override
    public GameState getGame(String gameId) {
        try {
            GameEngineResponse response = restClient.get()
                    .uri("/games/{gameId}", gameId)
                    .retrieve()
                .body(GameEngineResponse.class);
            return toGameState(response);
        } catch (Exception e) {
            throw new GameEngineException("Failed to get game: " + gameId, e);
        }
    }

    private GameState toGameState(GameEngineResponse response) {
        return new GameState(
            response.gameId(),
            response.board(),
            SessionPlayer.valueOf(response.currentTurn()),
            org.ihor.gamesession.SessionGameStatus.valueOf(response.status())
        );
    }
}

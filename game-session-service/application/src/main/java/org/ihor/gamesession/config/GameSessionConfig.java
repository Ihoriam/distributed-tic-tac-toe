package org.ihor.gamesession.config;

import org.ihor.gamesession.GameEngineGateway;
import org.ihor.gamesession.RandomMoveGenerator;
import org.ihor.gamesession.SessionRepository;
import org.ihor.gamesession.SessionService;
import org.ihor.gamesession.persistence.InMemorySessionRepository;
import org.ihor.gamesession.gameengine.GameEngineClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GameSessionConfig {

    @Bean
    public SessionRepository sessionRepository() {
        return new InMemorySessionRepository();
    }

    @Bean
    public GameEngineGateway gameEngineGateway(@Value("${game.engine.base-url}") String baseUrl) {
        return new GameEngineClient(baseUrl);
    }

    @Bean
    public RandomMoveGenerator randomMoveGenerator() {
        return new RandomMoveGenerator(new Random());
    }

    @Bean
    public SessionService sessionService(SessionRepository sessionRepository,
                                         GameEngineGateway gameEngineGateway,
                                         RandomMoveGenerator randomMoveGenerator) {
        return new SessionService(sessionRepository, gameEngineGateway, randomMoveGenerator);
    }
}

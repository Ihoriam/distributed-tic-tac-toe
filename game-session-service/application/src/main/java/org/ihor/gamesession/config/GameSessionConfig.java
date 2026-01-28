package org.ihor.gamesession.config;

import org.ihor.gamesession.*;
import org.ihor.gamesession.gameengine.GameEngineClient;
import org.ihor.gamesession.persistence.InMemorySessionRepository;
import org.ihor.gamesession.websocket.WebSocketSimulationPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Bean
    public ExecutorService simulationExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public SimulationUpdatePublisher simulationUpdatePublisher(SimpMessagingTemplate messagingTemplate) {
        return new WebSocketSimulationPublisher(messagingTemplate);
    }
}

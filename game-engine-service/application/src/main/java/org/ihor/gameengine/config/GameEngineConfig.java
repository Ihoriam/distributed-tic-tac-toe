package org.ihor.gameengine.config;

import org.ihor.gameengine.GameRepository;
import org.ihor.gameengine.GameService;
import org.ihor.gameengine.persistence.InMemoryGameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameEngineConfig {

    @Bean
    public GameRepository gameRepository() {
        return new InMemoryGameRepository();
    }

    @Bean
    public GameService gameService(GameRepository gameRepository) {
        return new GameService(gameRepository);
    }
}

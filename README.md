# Distributed Tic Tac Toe

A microservices-based Tic Tac Toe system where two independent Spring Boot services automatically play games against
each other. A web UI displays the board in real-time via WebSockets as moves are generated.

## Tech Stack

- **Java 21**, **Spring Boot 4.0.2**, **Gradle 9.3.0**
- SpringDoc OpenAPI (Swagger UI)
- STOMP over SockJS for real-time WebSocket updates
- Thymeleaf for server-side HTML rendering

## Services

| Service          | Port | Description                                                                    |
|------------------|------|--------------------------------------------------------------------------------|
| **Game Engine**  | 8081 | Core game logic: board state, move validation, outcomes                        |
| **Game Session** | 8082 | Orchestrates sessions, generates random moves, publishes updates via WebSocket |
| **UI**           | 8080 | Web frontend with live board, speed control, move history                      |

## Architecture

Each microservice follows **Clean Architecture** with strictly separated modules:

```
game-engine-service/
├── domain/                        # Pure Java: Game, Board, Player, MoveResult, GameService
├── infrastructure/
│   ├── persistence/               # InMemoryGameRepository
│   └── rest/                      # GameController, DTOs (with Bean Validation)
└── application/                   # Spring Boot entry point and config

game-session-service/
├── domain/                        # Pure Java: Session, SessionPlayer, SessionService, RandomMoveGenerator
├── infrastructure/
│   ├── persistence/               # InMemorySessionRepository
│   ├── game-engine-integration/   # GameEngineClient (REST client to game-engine-service)
│   ├── rest/                      # SessionController, DTOs
│   └── websocket/                 # WebSocketSimulationPublisher, STOMP config
└── application/                   # Spring Boot entry point and config

ui-service/                        # Thymeleaf web app with SockJS/STOMP client
```

**Key rules:**

- Domain modules have **zero Spring dependencies** — pure Java only.
- Infrastructure depends on domain, never the reverse.
- Application modules wire everything via Spring `@Configuration` classes.

## Note on Repository Structure

In a real-world project, each service (Game Engine, Game Session, UI) would live in its own repository with independent
CI/CD pipelines and deployment lifecycles. They are kept in a single monorepo here as this is a test task for a job
application, making it easier to review, clone, and run everything in one place.

## Getting Started

### Prerequisites

- Java 21+
- No external databases or message brokers required (all in-memory)

### Build

```bash
./gradlew build
```

### Run

Start all three services (each in a separate terminal):

```bash
./gradlew :game-engine-service:application:bootRun
./gradlew :game-session-service:application:bootRun
./gradlew :ui-service:bootRun
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

### Run with IntelliJ IDEA

The project includes shared run configurations in the `.run/` directory. IntelliJ IDEA picks them up automatically when
you open the project.

| Configuration              | Description                                                |
|----------------------------|------------------------------------------------------------|
| **GameEngineApplication**  | Starts the Game Engine Service                             |
| **GameSessionApplication** | Starts the Game Session Service                            |
| **UiApplication**          | Starts the UI Service                                      |
| **RunAll**                 | Compound configuration — starts all three services at once |

Select **RunAll** from the run configurations dropdown and click Run to launch the entire system in one step.

### Run Tests

```bash
./gradlew test
```

Run a single test:

```bash
./gradlew :game-engine-service:domain:test --tests "org.ihor.gameengine.GameTest"
```

## API Endpoints

### Game Engine Service (port 8081)

| Method | Endpoint               | Description       |
|--------|------------------------|-------------------|
| POST   | `/games`               | Create a new game |
| GET    | `/games/{gameId}`      | Get game state    |
| POST   | `/games/{gameId}/move` | Make a move       |

### Game Session Service (port 8082)

| Method | Endpoint                                        | Description                             |
|--------|-------------------------------------------------|-----------------------------------------|
| POST   | `/sessions`                                     | Create a new session                    |
| GET    | `/sessions/{sessionId}`                         | Get session details                     |
| POST   | `/sessions/{sessionId}/simulate`                | Run full simulation synchronously       |
| POST   | `/sessions/{sessionId}/simulate-async?delayMs=` | Run simulation async with speed control |

Swagger UI is available at `/docs` on both services.

### WebSocket

- **Endpoint:** `ws://localhost:8082/ws` (SockJS)
- **Topic:** `/topic/session/{sessionId}` — receives move updates and game completion events

## UI Features

- Animated 3x3 game board with color-coded X (blue) and O (red)
- Adjustable simulation speed slider (20ms-2000ms per move)
- Live connection status indicator
- Scrollable move history panel
- Loading states and error handling

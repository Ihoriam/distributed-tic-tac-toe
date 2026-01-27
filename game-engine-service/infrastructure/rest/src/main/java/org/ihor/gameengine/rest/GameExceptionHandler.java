package org.ihor.gameengine.rest;

import org.ihor.gameengine.exceptions.GameNotFoundException;
import org.ihor.gameengine.exceptions.GameOverException;
import org.ihor.gameengine.exceptions.InvalidMoveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(GameNotFoundException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(InvalidMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidMove(InvalidMoveException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(GameOverException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleGameOver(GameOverException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException e) {
        return Map.of("error", e.getMessage());
    }
}

package org.ihor.gamesession.models;

public enum SessionPlayer {
    X, O;

    public SessionPlayer opposite() {
        return this == X ? O : X;
    }
}

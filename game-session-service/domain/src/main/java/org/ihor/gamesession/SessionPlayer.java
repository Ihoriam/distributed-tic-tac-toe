package org.ihor.gamesession;

public enum SessionPlayer {
    X, O;

    public SessionPlayer opposite() {
        return this == X ? O : X;
    }
}

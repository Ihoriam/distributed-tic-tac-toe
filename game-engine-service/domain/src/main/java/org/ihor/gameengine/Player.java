package org.ihor.gameengine;

public enum Player {
    X, O;

    public Player opposite() {
        return this == X ? O : X;
    }
}

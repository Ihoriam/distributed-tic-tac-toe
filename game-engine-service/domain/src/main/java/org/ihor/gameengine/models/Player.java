package org.ihor.gameengine.models;

public enum Player {
    X, O;

    public Player opposite() {
        return this == X ? O : X;
    }
}

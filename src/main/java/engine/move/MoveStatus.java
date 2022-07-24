/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

/** Tells whether the move is possible or not, including the reason. */
public enum MoveStatus {
    DONE,
    ILLEGAL,
    LEAVES_PLAYER_IN_CHECK;

    public boolean isDone() {
        return this == DONE;
    }
}

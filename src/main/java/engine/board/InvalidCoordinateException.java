/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.board;

public class InvalidCoordinateException extends RuntimeException {

    public InvalidCoordinateException(String message) {
        super(message);
    }
}

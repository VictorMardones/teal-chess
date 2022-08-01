/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

/** An exception that is thrown when trying to access coordinate outside of the chessboard. */
public class InvalidCoordinateException extends RuntimeException {

  /**
   * Constructs a new invalid coordinate exception with the specified message.
   *
   * @param message The detail message, specifying the cause of the exception being thrown
   */
  public InvalidCoordinateException(final String message) {
    super(message);
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import lombok.EqualsAndHashCode;

/** A move where a piece captures another piece. */
@EqualsAndHashCode(callSuper = true)
public class CaptureMove extends Move {

  public CaptureMove(
      final Board board,
      final Piece piece,
      final Coordinate destination,
      final Piece capturedPiece) {
    super(board, piece, destination);

    this.capturedPiece = capturedPiece;
  }

  @Override
  public Board execute() {
    return board
        .nextTurnBuilder()
        .withoutPiece(piece)
        .withoutPiece(capturedPiece)
        .piece(piece.move(this))
        .build();
  }

  @Override
  public String toString() {
    return piece.toSingleChar() + getDestination().toString();
  }
}

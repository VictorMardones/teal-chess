/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Pawn;
import cl.vmardones.chess.engine.piece.Piece;
import lombok.EqualsAndHashCode;

/** A move where a pawn captures another piece. */
@EqualsAndHashCode(callSuper = true)
public class PawnCaptureMove extends CaptureMove {
  public PawnCaptureMove(
      final Board board, final Pawn pawn, final Coordinate destination, final Piece capturedPiece) {
    super(board, pawn, destination, capturedPiece);
  }

  @Override
  public String toString() {
    return String.format("%sx%s", piece.getPosition().toString().charAt(0), getDestination());
  }
}

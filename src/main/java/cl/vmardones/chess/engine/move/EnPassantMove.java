/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Pawn;
import cl.vmardones.chess.engine.piece.Piece;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

// TODO: Fix implementation, highlighted moves don't match moves that are executed after clicking
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class EnPassantMove extends CaptureMove {
  public EnPassantMove(
      final Board board, final Pawn pawn, final Coordinate destination, final Piece capturedPiece) {
    super(board, pawn, destination, capturedPiece);
  }
}

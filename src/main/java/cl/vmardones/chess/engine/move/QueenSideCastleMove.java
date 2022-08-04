/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.piece.Rook;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class QueenSideCastleMove extends CastleMove {

  public QueenSideCastleMove(
      final Board board,
      final Piece piece,
      final Coordinate destination,
      final Rook rook,
      final Coordinate rookDestination) {
    super(board, piece, destination, rook, rookDestination);
  }

  @Override
  public String toString() {
    return "0-0-0";
  }
}

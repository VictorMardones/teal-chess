/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.piece.Rook;

abstract class CastleMove extends Move {
  protected final Rook rook;
  protected final Coordinate rookDestination;

  protected CastleMove(
      final Board board,
      final Piece piece,
      final Coordinate destination,
      final Rook rook,
      final Coordinate rookDestination) {
    super(board, piece, destination);
    this.rook = rook;
    this.rookDestination = rookDestination;
    castling = true;
  }

  @Override
  public Board execute() {
    return board
        .nextTurnBuilder()
        .withoutPiece(piece)
        .withoutPiece(rook)
        .piece(piece.move(this))
        .piece(getMovedRook())
        .build();
  }

  private Rook getMovedRook() {
    return new Rook(rookDestination, rook.getAlliance(), false);
  }
}

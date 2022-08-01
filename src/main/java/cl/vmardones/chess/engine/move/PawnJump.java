/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Pawn;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PawnJump extends Move {
  public PawnJump(final Board board, final Pawn pawn, final Coordinate destination) {
    super(board, pawn, destination);
  }

  @Override
  public Board execute() {
    return board
        .nextTurnBuilder()
        .withoutPiece(piece)
        .piece(piece.move(this))
        .enPassantPawn((Pawn) piece.move(this))
        .build();
  }
}

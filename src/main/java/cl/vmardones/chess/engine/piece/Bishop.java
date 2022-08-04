/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.vector.Diagonal;
import cl.vmardones.chess.engine.piece.vector.Vector;
import cl.vmardones.chess.engine.player.Alliance;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** The bishop piece. It can move diagonally. */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public final class Bishop implements SlidingPiece {

  private static final Collection<int[]> MOVE_VECTORS = calculateMoveVectors();

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Bishop(final Coordinate position, final Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }

  @Override
  public Bishop move(final Move move) {
    return new Bishop(move.getDestination(), alliance, false);
  }

  @Override
  public Collection<int[]> getMoveVectors() {
    return MOVE_VECTORS;
  }

  private static Collection<int[]> calculateMoveVectors() {
    return Arrays.stream(Diagonal.values())
        .map(Vector::getVector)
        .collect(ImmutableList.toImmutableList());
  }
}

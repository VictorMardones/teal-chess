/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

/**
 * A piece that can move to a specific set of positions. It usually doesn't matter if there are
 * other pieces in the way.
 */
interface JumpingPiece extends Piece {

  Collection<int[]> getMoveOffsets();

  @Override
  default Collection<Coordinate> calculatePossibleDestinations(@NotNull final Board board) {
    return getMoveOffsets().stream()
        .map(offset -> getPosition().to(offset[0], offset[1]))
        .flatMap(Optional::stream)
        .collect(ImmutableList.toImmutableList());
  }
}

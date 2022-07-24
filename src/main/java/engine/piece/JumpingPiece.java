/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import java.util.Collection;
import java.util.Optional;

/**
 * A piece that can move to a specific set of positions. It usually doesn't matter if there are other
 * pieces in the way.
 */
public interface JumpingPiece extends Piece {

    Collection<int[]> getMoveOffsets();

    @Override
    default Collection<Coordinate> calculatePossibleDestinations() {
        return getMoveOffsets().stream()
                .map(offset -> getPosition().to(offset[0], offset[1]))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }
}

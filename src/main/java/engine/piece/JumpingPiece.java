package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * A piece that can move to a specific set of positions. It doesn't matter if there are another
 * pieces in the way.
 */
public interface JumpingPiece extends Piece {

    int[][] getMoveOffsets();

    @Override
    default Collection<Coordinate> calculatePossibleDestinations() {
        return Arrays.stream(getMoveOffsets())
                .map(offset -> getPosition().to(offset[0], offset[1]))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }
}

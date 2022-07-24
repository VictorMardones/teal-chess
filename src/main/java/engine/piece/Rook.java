package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** The rook piece. It can move horizontally and vertically. */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Rook implements SlidingPiece {

    private static final Collection<int[]> MOVE_VECTORS = calculateMoveVectors();

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Rook(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public Rook move(final Move move) {
        return new Rook(move.getDestination(), alliance, false);
    }

    private static Collection<int[]> calculateMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public Collection<int[]> getMoveVectors() {
        return Stream.concat(Arrays.stream(Vector.Horizontal.values()), Arrays.stream(Vector.Vertical.values()))
                .map(Vector::getVector)
                .collect(ImmutableList.toImmutableList());
    }
}

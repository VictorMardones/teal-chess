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

/** The queen, strongest piece in the game. It can move horizontally, vertically and diagonally. */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Queen implements SlidingPiece {

    private static final Collection<int[]> MOVE_VECTORS = calculateMoveVectors();

    private Coordinate position;
    private Alliance alliance;
    private boolean firstMove;

    public Queen(Coordinate position, Alliance alliance) {
        this(position, alliance, true);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Queen move(final Move move) {
        return new Queen(move.getDestination(), alliance, false);
    }

    @Override
    public Collection<int[]> getMoveVectors() {
        return MOVE_VECTORS;
    }

    private static Collection<int[]> calculateMoveVectors() {
        return Stream.concat(
                        Arrays.stream(Vector.Diagonal.values()),
                        Stream.concat(
                                Arrays.stream(Vector.Horizontal.values()), Arrays.stream(Vector.Vertical.values())))
                .map(Vector::getVector)
                .collect(ImmutableList.toImmutableList());
    }
}

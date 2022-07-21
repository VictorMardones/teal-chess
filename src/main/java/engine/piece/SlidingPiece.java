package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardUtils;
import engine.move.Move;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * A piece that can move in a specific set of directions.
 */
public interface SlidingPiece extends Piece {

    int[] getMoveVectors();

    @Override
    default Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        return Arrays.stream(getMoveVectors())
                .mapMulti(this::calculateOffsets)
                .filter(this::isInMoveRange)
                .mapToObj(board::getTile)
                .filter(Objects::nonNull)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .filter(Objects::nonNull)
                .collect(ImmutableList.toImmutableList());
    }

    private void calculateOffsets(final int vector, final IntConsumer consumer) {
        var multiplier = 1;

        while (BoardUtils.isInsideBoard(getPosition() + vector * multiplier)) {
            consumer.accept(getPosition() + vector * multiplier);
            multiplier++;
        }
    }
}

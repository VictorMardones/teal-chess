package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.IntConsumer;

/**
 * A piece that can move in a specific set of directions.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SlidingPiece implements Piece {

    protected int position;
    protected Alliance alliance;
    protected BoardService boardService;

    abstract int[] getMoveVectors();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        return Arrays.stream(getMoveVectors())
                .mapMulti(this::calculateOffsets)
                .filter(this::isInMoveRange)
                .mapToObj(board::getTile)
                .filter(this::isAccessible)
                .map(tile -> createMove(this, tile, board))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(ImmutableList.toImmutableList());
    }

    private void calculateOffsets(final int vector, final IntConsumer consumer) {
        var multiplier = 1;

        while (getBoardService().isInside(getPosition() + vector * multiplier)) {
            consumer.accept(getPosition() + vector * multiplier);
            multiplier++;
        }
    }
}

package engine.piece;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.move.Move;
import com.google.common.collect.ImmutableList;
import engine.player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * A piece that can move in a specific set of directions.
 */
public abstract class SlidingPiece extends Piece {

    protected SlidingPiece(int position, Alliance alliance, PieceType pieceType) {
        super(position, alliance, pieceType);
    }

    abstract int[] getMoveVectors();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        // TODO: Remove these non-null filters, change how the methods work
        final var legalMoves = Arrays.stream(getMoveVectors())
                .mapMulti(this::calculateOffsets)
                .filter(this::isLegalMove)
                .mapToObj(board::getTile)
                .filter(Objects::nonNull)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
                .filter(Objects::nonNull)
                .toList();

        return ImmutableList.copyOf(legalMoves);
    }

    private void calculateOffsets(final int vector, final IntConsumer consumer) {
        var multiplier = 1;

        while (BoardUtils.isInsideBoard(position + vector * multiplier)) {
            consumer.accept(position + vector * multiplier);
            multiplier++;
        }
    }
}

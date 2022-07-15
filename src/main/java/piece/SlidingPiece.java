package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.IntConsumer;

public abstract class SlidingPiece extends Piece {

    protected SlidingPiece(int position, Alliance alliance) {
        super(position, alliance);
    }

    abstract int[] getMoveVectors();

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final var legalMoves = Arrays.stream(getMoveVectors())
                .mapMulti(this::calculateOffsets)
                .filter(offset -> !isIllegalMove(offset))
                .mapToObj(board::getTile)
                .filter(tile -> PieceUtils.isAccessible(this, tile))
                .map(tile -> PieceUtils.createMove(this, tile, board))
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

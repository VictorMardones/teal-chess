package piece;

import board.Board;
import board.BoardUtils;
import board.Move;
import board.Move.CaptureMove;
import board.Move.NormalMove;
import board.Tile;
import com.google.common.collect.ImmutableList;
import player.Alliance;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

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
                .filter(tile -> !isBlocked(tile))
                .map(tile -> createMove(board, tile))
                .collect(Collectors.toList());

        return ImmutableList.copyOf(legalMoves);
    }

    private void calculateOffsets(final int vector, final IntConsumer consumer) {
        var multiplier = 1;

        while (BoardUtils.isInsideBoard(position + vector * multiplier)) {
            consumer.accept(position + vector * multiplier);
            multiplier++;
        }
    }

    private boolean isBlocked(final Tile destination) {
        return destination.isOccupied() && sameAliance(destination.getPiece());
    }

    private Move createMove(final Board board, final Tile destination) {
        if (!destination.isOccupied()) {
            return new NormalMove(board, this, destination.getCoordinate());
        }

        final var capturablePiece = destination.getPiece();

        if (!sameAliance(capturablePiece)) {
            return new CaptureMove(board, this, destination.getCoordinate(), capturablePiece);
        }

        return null;
    }
}

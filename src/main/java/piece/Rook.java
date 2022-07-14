package piece;

import board.BoardUtils;
import player.Alliance;

/**
 * The rook piece.
 * It moves horizontally and vertically.
 */
public final class Rook extends SlidingPiece {

    private final static int[] CANDIDATE_MOVE_VECTORS = {-8, -1, 1, 8};

    public Rook(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    int[] getMoveVectors() {
        return CANDIDATE_MOVE_VECTORS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return !BoardUtils.sameRow(position, destination) &&
                !BoardUtils.sameColumn(position, destination);
    }
}

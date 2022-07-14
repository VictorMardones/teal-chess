package piece;

import board.BoardUtils;
import player.Alliance;

/**
 * The bishop piece.
 * It moves diagonally.
 */
public final class Bishop extends SlidingPiece {

    private final static int[] CANDIDATE_MOVE_VECTORS = {-9, -7, 7, 9};

    Bishop(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    int[] getMoveVectors() {
        return CANDIDATE_MOVE_VECTORS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return !BoardUtils.sameColor(position, destination);
    }
}

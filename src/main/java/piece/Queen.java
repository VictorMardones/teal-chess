package piece;

import board.BoardUtils;
import player.Alliance;

public final class Queen extends SlidingPiece {

    private static final int[] CANDIDATE_MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    int[] getMoveVectors() {
        return CANDIDATE_MOVE_VECTORS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return !BoardUtils.sameRow(position, destination) &&
                !BoardUtils.sameColumn(position, destination) &&
                !BoardUtils.sameColor(position, destination);
    }
}

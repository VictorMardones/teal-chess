package piece;

import board.BoardUtils;
import player.Alliance;

/**
 * The queen, strongest piece in the game.
 * It can move horizontally, vertically and diagonally.
 */
public final class Queen extends SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(int position, Alliance alliance) {
        super(position, alliance, PieceType.QUEEN);
    }

    @Override
    int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return !BoardUtils.sameRow(position, destination) &&
                !BoardUtils.sameColumn(position, destination) &&
                !BoardUtils.sameColor(position, destination);
    }
}

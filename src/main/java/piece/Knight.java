package piece;

import board.BoardUtils;
import player.Alliance;

/**
 * The knight piece.
 * It moves in an L shape.
 */
public final class Knight extends JumpingPiece {

    private static final int[] MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    int[] getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return BoardUtils.sameColor(position, destination);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.getPieceName();
    }
}

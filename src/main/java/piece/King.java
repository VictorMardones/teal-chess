package piece;

import board.BoardUtils;
import player.Alliance;

public final class King extends JumpingPiece {

    private static final int[] MOVE_OFFSETS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(int position, Alliance alliance) {
        super(position, alliance);
    }

    @Override
    int[] getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    @Override
    protected boolean isIllegalMove(int destination) {
        return Math.abs(BoardUtils.getColumn(position) - BoardUtils.getColumn(destination)) > 1;
    }

    @Override
    public String toString() {
        return PieceType.KING.getPieceName();
    }
}

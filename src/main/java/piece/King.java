package piece;

import board.BoardUtils;
import board.Move;
import player.Alliance;

/**
 * The king piece.
 * The most important piece in the game, must be defended at all costs.
 * It moves like the queen, but only one space at a time.
 * It also cannot move into a position where it could be captured.
 */
public final class King extends JumpingPiece {

    private static final int[] MOVE_OFFSETS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(int position, Alliance alliance) {
        super(position, alliance, PieceType.KING);
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
    public King movePiece(final Move move) {
        return new King(move.getDestination(), move.getPiece().alliance);
    }
}

package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The knight piece.
 * It moves in an L shape.
 */
public final class Knight extends JumpingPiece {

    private static final int[] MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int position, Alliance alliance) {
        super(position, alliance, PieceType.KNIGHT);
    }

    @Override
    int[] getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    @Override
    public boolean isLegalMove(int destination) {
        return !BoardUtils.sameColor(position, destination);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestination(), alliance);
    }
}
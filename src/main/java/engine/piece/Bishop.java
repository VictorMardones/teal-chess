package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The bishop piece.
 * It can move diagonally.
 */
public final class Bishop extends SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -7, 7, 9};

    public Bishop(int position, Alliance alliance) {
        super(position, alliance, PieceType.BISHOP);
    }

    @Override
    int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public boolean isLegalMove(int destination) {
        return BoardUtils.sameColor(position, destination);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getDestination(), alliance);
    }
}

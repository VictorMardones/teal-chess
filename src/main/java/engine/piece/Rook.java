package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The rook piece.
 * It can move horizontally and vertically.
 */
public final class Rook extends SlidingPiece {

    private static final int[] MOVE_VECTORS = {-8, -1, 1, 8};

    public Rook(int position, Alliance alliance) {
        super(position, alliance, PieceType.ROOK);
    }

    @Override
    int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public boolean isLegalMove(int destination) {
        return BoardUtils.sameRank(position, destination) ||
                BoardUtils.sameColumn(position, destination);
    }

    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getDestination(), alliance);
    }
}

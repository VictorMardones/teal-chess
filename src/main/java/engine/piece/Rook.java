package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The rook piece.
 * It can move horizontally and vertically.
 */
@Getter
@AllArgsConstructor
public final class Rook implements SlidingPiece {

    private static final int[] MOVE_VECTORS = {-8, -1, 1, 8};

    private int position;
    private Alliance alliance;

    @Override
    public int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isInMoveRange(int destination) {
        return BoardUtils.sameRank(position, destination) ||
                BoardUtils.sameColumn(position, destination);
    }

    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getDestination(), alliance);
    }
}

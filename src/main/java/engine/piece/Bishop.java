package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The bishop piece.
 * It can move diagonally.
 */
@Getter
@AllArgsConstructor
public final class Bishop implements SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -7, 7, 9};

    private int position;
    private Alliance alliance;

    @Override
    public int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isInMoveRange(int destination) {
        return BoardUtils.sameColor(position, destination);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getDestination(), alliance);
    }
}

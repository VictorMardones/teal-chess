package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The knight piece.
 * It moves in an L shape.
 */
@Getter
@AllArgsConstructor
public final class Knight implements JumpingPiece {

    private int position;
    private Alliance alliance;

    private static final int[] MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    @Override
    public int[] getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public boolean isInMoveRange(int destination) {
        return !BoardUtils.sameColor(position, destination);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestination(), alliance);
    }
}

package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The king piece.
 * The most important piece in the game, must be defended at all costs.
 * It moves like the queen, but only one space at a time.
 * It also cannot move into a position where it could be captured.
 */
@Getter
@AllArgsConstructor
public final class King implements JumpingPiece {

    private int position;
    private Alliance alliance;

    private static final int[] MOVE_OFFSETS = {-9, -8, -7, -1, 1, 7, 8, 9};

    @Override
    public int[] getMoveOffsets() {
        return MOVE_OFFSETS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public boolean isInMoveRange(int destination) {
        return Math.abs(BoardUtils.getColumn(position) - BoardUtils.getColumn(destination)) <= 2;
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestination(), alliance);
    }
}

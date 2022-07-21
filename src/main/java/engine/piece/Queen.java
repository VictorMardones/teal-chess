package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The queen, strongest piece in the game.
 * It can move horizontally, vertically and diagonally.
 */
@Getter
@AllArgsConstructor
public final class Queen implements SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

    private int position;
    private Alliance alliance;

    @Override
    public int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean isInMoveRange(int destination) {
        return BoardUtils.sameRank(position, destination) ||
                BoardUtils.sameColumn(position, destination) ||
                BoardUtils.sameColor(position, destination);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestination(), alliance);
    }
}

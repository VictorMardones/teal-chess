package engine.piece;

import engine.board.BoardUtils;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The queen, strongest piece in the game.
 * It can move horizontally, vertically and diagonally.
 */
public final class Queen extends SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(int position, Alliance alliance) {
        super(position, alliance, PieceType.QUEEN);
    }

    @Override
    int[] getMoveVectors() {
        return MOVE_VECTORS;
    }

    @Override
    public boolean isLegalMove(int destination) {
        return BoardUtils.sameRow(position, destination) ||
                BoardUtils.sameColumn(position, destination) ||
                BoardUtils.sameColor(position, destination);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestination(), alliance);
    }
}

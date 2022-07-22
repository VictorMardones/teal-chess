package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The knight piece.
 * It moves in an L shape.
 */
public final class Knight extends JumpingPiece {

    private static final int[] MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int position, Alliance alliance, BoardService boardService) {
        super(position, alliance, boardService);
    }

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
        return !boardService.sameColor(position, destination);
    }

    @Override
    public Knight move(final Move move) {
        return new Knight(move.getDestination(), alliance, boardService);
    }
}

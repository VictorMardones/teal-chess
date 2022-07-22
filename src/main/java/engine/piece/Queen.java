package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;

/**
 * The queen, strongest piece in the game.
 * It can move horizontally, vertically and diagonally.
 */
public final class Queen extends SlidingPiece {

    private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(int position, Alliance alliance, BoardService boardService) {
        super(position, alliance, boardService);
    }

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
        return boardService.sameRank(position, destination) ||
                boardService.sameColumn(position, destination) ||
                boardService.sameColor(position, destination);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestination(), alliance, boardService);
    }
}

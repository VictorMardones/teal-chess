package move;

import board.Board;
import piece.Pawn;

/**
 * A move where a pawn gets to another tile.
 */
public final class PawnMove extends Move {
    public PawnMove(Board board, Pawn pawn, int destination) {
        super(board, pawn, destination);
    }
}

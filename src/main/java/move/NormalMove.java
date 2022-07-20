package move;

import board.Board;
import piece.Piece;

/**
 * A move where a piece gets to another tile.
 */
public final class NormalMove extends Move {
    public NormalMove(Board board, Piece piece, int destination) {
        super(board, piece, destination);
    }
}

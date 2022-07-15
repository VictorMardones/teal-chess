package board;

import lombok.AllArgsConstructor;
import piece.Piece;

/**
 * The action of moving a piece.
 */
@AllArgsConstructor
public abstract class Move {

    private final Board board;
    private final Piece piece;
    private final int destination;

    public static final class NormalMove extends Move {
        public NormalMove(Board board, Piece piece, int destination) {
            super(board, piece, destination);
        }
    }

    public static final class CaptureMove extends Move {

        final Piece attackedPiece;

        public CaptureMove(Board board, Piece piece, int destination, Piece attackedPiece) {
            super(board, piece, destination);
            this.attackedPiece = attackedPiece;
        }
    }
}

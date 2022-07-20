package move;

import board.Board;
import piece.Pawn;
import piece.Piece;

/**
 * A move where a pawn captures another piece.
 */
public class PawnCaptureMove extends CaptureMove {
    public PawnCaptureMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }
}

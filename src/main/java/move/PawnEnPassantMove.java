package move;

import board.Board;
import piece.Pawn;
import piece.Piece;

public final class PawnEnPassantMove extends PawnCaptureMove {
    public PawnEnPassantMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }
}

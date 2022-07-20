package engine.move;

import engine.board.Board;
import lombok.EqualsAndHashCode;
import engine.piece.Piece;

/**
 * A move where a piece captures another piece.
 */
@EqualsAndHashCode(callSuper = true)
public class CaptureMove extends Move {

    public CaptureMove(Board board, Piece piece, int destination, Piece capturedPiece) {
        super(board, piece, destination);

        this.capturedPiece = capturedPiece;
    }
}

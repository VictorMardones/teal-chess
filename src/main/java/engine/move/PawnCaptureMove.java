package engine.move;

import engine.board.Board;
import engine.piece.Pawn;
import engine.piece.Piece;

/** A move where a pawn captures another piece. */
public class PawnCaptureMove extends CaptureMove {
  public PawnCaptureMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
    super(board, pawn, destination, capturedPiece);
  }
}

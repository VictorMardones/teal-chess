package engine.move;

import engine.board.Board;
import engine.piece.Pawn;
import engine.piece.Piece;

public final class PawnEnPassantMove extends PawnCaptureMove {
  public PawnEnPassantMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
    super(board, pawn, destination, capturedPiece);
  }
}

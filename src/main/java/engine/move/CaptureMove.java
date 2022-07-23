package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;
import lombok.EqualsAndHashCode;

/** A move where a piece captures another piece. */
@EqualsAndHashCode(callSuper = true)
public class CaptureMove extends Move {

  public CaptureMove(Board board, Piece piece, Coordinate destination, Piece capturedPiece) {
    super(board, piece, destination);

    this.capturedPiece = capturedPiece;
  }
}

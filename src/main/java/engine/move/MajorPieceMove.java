package engine.move;

import engine.board.Board;
import engine.piece.Piece;

/** A move where a non-pawn piece gets to another tile. */
public final class MajorPieceMove extends Move {
  public MajorPieceMove(Board board, Piece piece, int destination) {
    super(board, piece, destination);
  }
}

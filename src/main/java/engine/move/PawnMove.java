package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Pawn;

/** A move where a pawn gets to another tile. */
public final class PawnMove extends Move {
  public PawnMove(Board board, Pawn pawn, Coordinate destination) {
    super(board, pawn, destination);
  }
}

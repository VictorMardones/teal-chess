package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;

/** The rook piece. It can move horizontally and vertically. */
public final class Rook extends SlidingPiece {

  private static final int[] MOVE_VECTORS = {-8, -1, 1, 8};

  public Rook(Coordinate position, Alliance alliance, boolean firstMove) {
    super(position, alliance, firstMove);
  }

  public Rook(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public int[] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }

  @Override
  public boolean isInMoveRange(Coordinate destination) {
    return position.sameRankAs(destination) || position.sameColumnAs(destination);
  }

  @Override
  public Rook move(final Move move) {
    return new Rook(move.getDestination(), alliance, false);
  }
}

package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;

/** The bishop piece. It can move diagonally. */
public final class Bishop extends SlidingPiece {

  private static final int[] MOVE_VECTORS = {-9, -7, 7, 9};

  public Bishop(Coordinate position, Alliance alliance, boolean firstMove) {
    super(position, alliance, firstMove);
  }

  public Bishop(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public int[] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }

  @Override
  public boolean isInMoveRange(Coordinate destination) {
    return position.sameColorAs(destination);
  }

  @Override
  public Bishop move(final Move move) {
    return new Bishop(move.getDestination(), alliance, false);
  }
}

package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;

/** The queen, strongest piece in the game. It can move horizontally, vertically and diagonally. */
public final class Queen extends SlidingPiece {

  private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

  public Queen(Coordinate position, Alliance alliance, boolean firstMove) {
    super(position, alliance, firstMove);
  }

  public Queen(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public int[] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.QUEEN;
  }

  @Override
  public boolean isInMoveRange(Coordinate destination) {
    return position.sameRankAs(destination)
        || position.sameColumnAs(destination)
        || position.sameColorAs(destination);
  }

  @Override
  public Queen move(final Move move) {
    return new Queen(move.getDestination(), alliance, false);
  }
}

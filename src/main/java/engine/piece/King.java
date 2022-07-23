package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.Getter;

/**
 * The king piece. The most important piece in the game, must be defended at all costs. It moves
 * like the queen, but only one space at a time. It also cannot move into a position where it could
 * be captured.
 */
@Getter
public final class King extends JumpingPiece {

  public King(Coordinate position, Alliance alliance, boolean firstMove) {
    super(position, alliance, firstMove);
  }

  public King(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public int[] getMoveOffsets() {
    return new int[] {-9, -8, -7, -1, 1, 7, 8, 9};
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
  }

  @Override
  public boolean isInMoveRange(Coordinate destination) {
    return Math.abs(position.getColumnIndex() - destination.getColumnIndex()) <= 2;
  }

  @Override
  public King move(final Move move) {
    return new King(move.getDestination(), alliance, false);
  }
}

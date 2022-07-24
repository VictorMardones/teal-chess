package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The king piece. The most important piece in the game, must be defended at all costs. It moves
 * like the queen, but only one space at a time. It also cannot move into a position where it could
 * be captured.
 */
@Getter
@AllArgsConstructor
public final class King implements JumpingPiece {

  private static final int[][] MOVE_OFFSETS = {
    {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}
  };

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public King(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
  }

  @Override
  public int[][] getMoveOffsets() {
    return MOVE_OFFSETS;
  }

  @Override
  public King move(final Move move) {
    return new King(move.getDestination(), alliance, false);
  }
}

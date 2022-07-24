package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** The knight piece. It moves in an L shape. */
@AllArgsConstructor
@Getter
public final class Knight implements JumpingPiece {

  private static final int[][] MOVE_OFFSETS = {
    {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}
  };

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Knight(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KNIGHT;
  }

  @Override
  public Knight move(final Move move) {
    return new Knight(move.getDestination(), alliance, false);
  }

  @Override
  public int[][] getMoveOffsets() {
    return MOVE_OFFSETS;
  }
}

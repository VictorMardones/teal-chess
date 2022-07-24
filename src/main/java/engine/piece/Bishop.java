package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** The bishop piece. It can move diagonally. */
@Getter
@AllArgsConstructor
public final class Bishop implements SlidingPiece {

  private static final int[][] MOVE_VECTORS = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Bishop(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }

  @Override
  public int[][] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public Bishop move(final Move move) {
    return new Bishop(move.getDestination(), alliance, false);
  }
}

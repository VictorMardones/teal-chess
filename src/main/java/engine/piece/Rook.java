package engine.piece;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** The rook piece. It can move horizontally and vertically. */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Rook implements SlidingPiece {

  private static final int[][] MOVE_VECTORS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Rook(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }

  @Override
  public int[][] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public Rook move(final Move move) {
    return new Rook(move.getDestination(), alliance, false);
  }
}

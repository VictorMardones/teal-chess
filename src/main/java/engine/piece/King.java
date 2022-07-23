package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

/**
 * The king piece. The most important piece in the game, must be defended at all costs. It moves
 * like the queen, but only one space at a time. It also cannot move into a position where it could
 * be captured.
 */
@Getter
@AllArgsConstructor
public final class King implements Piece {

  private static final int[] MOVE_OFFSETS = {-9, -8, -7, -1, 1, 7, 8, 9};

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
  public Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(MOVE_OFFSETS)
            .map(offset -> getPosition().index() + offset)
            .mapToObj(Coordinate::new)
            .filter(destination -> Math.abs(position.getColumnIndex() - destination.getColumnIndex()) <= 2)
            .collect(ImmutableList.toImmutableList());
  }

  @Override
  public King move(final Move move) {
    return new King(move.getDestination(), alliance, false);
  }
}

package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;

import java.util.Arrays;
import java.util.Collection;

/** The knight piece. It moves in an L shape. */
public final class Knight extends JumpingPiece {

  private static final int[] MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

  public Knight(Coordinate position, Alliance alliance, boolean firstMove) {
    super(position, alliance, firstMove);
  }

  public Knight(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(MOVE_OFFSETS)
        .map(offset -> getPosition().index() + offset)
        .mapToObj(Coordinate::new)
        .filter(destination -> !position.sameColorAs(destination))
        .collect(ImmutableList.toImmutableList());
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KNIGHT;
  }

  @Override
  public Knight move(final Move move) {
    return new Knight(move.getDestination(), alliance, false);
  }
}

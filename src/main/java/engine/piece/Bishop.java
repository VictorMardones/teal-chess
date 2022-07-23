package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

/** The bishop piece. It can move diagonally. */
@Getter
@AllArgsConstructor
public final class Bishop implements Piece {

  private static final int[] MOVE_VECTORS = {-9, -7, 7, 9};

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
  public Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(MOVE_VECTORS)
            .mapToObj(this::calculateOffsets)
            .flatMap(Collection::stream)
            .collect(ImmutableList.toImmutableList());
  }

  private Collection<Coordinate> calculateOffsets(int vector) {
    return IntStream.range(1, Board.NUMBER_OF_RANKS + 1)
            .map(i -> getPosition().index() + vector * i)
            .mapToObj(Coordinate::new)
            .filter(destination -> position.sameColorAs(destination))
            .collect(ImmutableList.toImmutableList());
  }

  @Override
  public Bishop move(final Move move) {
    return new Bishop(move.getDestination(), alliance, false);
  }
}

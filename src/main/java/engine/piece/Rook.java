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

/** The rook piece. It can move horizontally and vertically. */
@Getter
@AllArgsConstructor
public final class Rook implements Piece {

  private static final int[] MOVE_VECTORS = {-8, -1, 1, 8};

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
            .filter(destination -> position.sameRankAs(destination) || position.sameColumnAs(destination))
            .collect(ImmutableList.toImmutableList());
  }

  @Override
  public Rook move(final Move move) {
    return new Rook(move.getDestination(), alliance, false);
  }
}

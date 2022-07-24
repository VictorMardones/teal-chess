package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

public interface SlidingPiece extends Piece {

  int[][] getMoveVectors();

  @Override
  default Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(getMoveVectors())
        .map(this::calculateOffsets)
        .flatMap(Collection::stream)
        .collect(ImmutableList.toImmutableList());
  }

  private Collection<Coordinate> calculateOffsets(int[] vector) {
    return IntStream.range(1, Board.NUMBER_OF_RANKS + 1)
        .mapToObj(i -> getPosition().to(vector[0] * i, vector[1] * i))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }
}

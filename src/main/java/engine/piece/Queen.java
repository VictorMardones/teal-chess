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

/** The queen, strongest piece in the game. It can move horizontally, vertically and diagonally. */
@Getter
@AllArgsConstructor
public final class Queen implements Piece {

  private static final int[] MOVE_VECTORS = {-9, -8, -7, -1, 1, 7, 8, 9};

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Queen(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.QUEEN;
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
            .filter(destination -> position.sameRankAs(destination)
                    || position.sameColumnAs(destination)
                    || position.sameColorAs(destination))
            .collect(ImmutableList.toImmutableList());
  }

  @Override
  public Queen move(final Move move) {
    return new Queen(move.getDestination(), alliance, false);
  }
}

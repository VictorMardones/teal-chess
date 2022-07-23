package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import java.util.*;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** A piece that can move in a specific set of directions. */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SlidingPiece implements Piece {

  protected int position;
  protected Alliance alliance;
  protected BoardService boardService;

  abstract int[] getMoveVectors();

  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {

    return Arrays.stream(getMoveVectors())
        .mapToObj(this::calculateOffsets)
        .flatMap(Collection::stream)
        .distinct()
        .filter(this::isInMoveRange)
        .map(board::getTile)
        .filter(this::isAccessible)
        .map(tile -> createMove(this, tile, board))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }

  private Collection<Integer> calculateOffsets(final int vector) {

    return IntStream.range(1, BoardService.NUMBER_OF_RANKS + 1)
        .map(i -> getPosition() + vector * i)
        .filter(destination -> getBoardService().isInside(destination))
        .boxed()
        .collect(ImmutableList.toImmutableList());
  }
}

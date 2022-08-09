/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.IntStream;

interface SlidingPiece extends Piece {

  Collection<int[]> getMoveVectors();

  @Override
  default Collection<Coordinate> calculatePossibleDestinations(@NotNull final Board board) {
    return getMoveVectors().stream()
        .map(vector -> calculateOffsets(vector, board))
        .flatMap(Collection::stream)
        .collect(ImmutableList.toImmutableList());
  }

  private Collection<Coordinate> calculateOffsets(final int[] vector, final Board board) {
    final var tiles =
        IntStream.range(1, Board.SIDE_LENGTH + 1)
            .mapToObj(i -> getPosition().to(vector[0] * i, vector[1] * i))
            .flatMap(Optional::stream)
            .map(board::getTile)
            .toList()
            .listIterator();

    return filterAccessible(tiles).stream()
        .map(Tile::getCoordinate)
        .collect(ImmutableList.toImmutableList());
  }

  // TODO: Replace this method with something more stream-friendly
  private Collection<Tile> filterAccessible(final Iterator<Tile> tiles) {

    final List<Tile> accessibleTiles = new ArrayList<>();

    while (tiles.hasNext()) {
      final var destination = tiles.next();
      final var pieceAtDestination = destination.getPiece();

      if (pieceAtDestination.isEmpty()) {
        accessibleTiles.add(destination);
      } else {
        if (isEnemyOf(pieceAtDestination.get())) {
          accessibleTiles.add(destination);
        }

        break;
      }
    }

    return accessibleTiles;
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

public interface SlidingPiece extends Piece {

    Collection<int[]> getMoveVectors();

    @Override
    default Collection<Coordinate> calculatePossibleDestinations(final Board board) {
        return getMoveVectors().stream()
                .map(vector -> calculateOffsets(vector, board))
                .flatMap(Collection::stream)
                .collect(ImmutableList.toImmutableList());
    }

    private Collection<Coordinate> calculateOffsets(int[] vector, final Board board) {
        return IntStream.range(1, Board.NUMBER_OF_RANKS + 1)
                .mapToObj(i -> getPosition().to(vector[0] * i, vector[1] * i))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(board::getTile)
                .takeWhile(this::isAccessible)
                .map(Tile::getCoordinate)
                .collect(ImmutableList.toImmutableList());
    }
}
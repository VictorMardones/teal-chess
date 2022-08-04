/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.piece.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TileTest {

  Tile tile;

  @Mock Coordinate coordinate;
  @Mock Piece piece;

  @Test
  void createOccupied() {
    assertThat(Tile.create(coordinate, piece).getPiece()).isPresent();
  }

  @Test
  void createEmpty() {
    assertThat(Tile.create(coordinate, null).getPiece()).isEmpty();
  }

  @Test
  void cache() {
    assertThat(Tile.create(Coordinate.of("g5"), null))
        .isEqualTo(Tile.create(Coordinate.of("g5"), null));
  }

  @Test
  void whitePieceToString() {
    when(piece.isBlack()).thenReturn(false);
    when(piece.toSingleChar()).thenReturn("P");

    assertThat(Tile.create(coordinate, piece)).hasToString("P");
  }

  @Test
  void blackPieceToString() {
    when(piece.isBlack()).thenReturn(true);
    when(piece.toSingleChar()).thenReturn("N");

    assertThat(Tile.create(coordinate, piece)).hasToString("n");
  }

  @Test
  void emptyToString() {
    assertThat(Tile.create(coordinate, null)).hasToString("-");
  }

  @Test
  void getCoordinate() {
    assertThat(Tile.create(Coordinate.of("c7"), null).getCoordinate())
        .isNotNull()
        .isEqualTo(Coordinate.of("c7"));
  }
}

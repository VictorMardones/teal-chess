/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.vector.LShaped;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KnightTest {

  Knight knight;

  Coordinate coordinate = Coordinate.of("c1");
  Coordinate destination = Coordinate.of("d1");

  @Mock Move move;

  @BeforeEach
  void setUp() {
    knight = new Knight(coordinate, Alliance.WHITE);
  }

  @Test
  void constructor() {
    assertThat(new Knight(coordinate, Alliance.BLACK)).matches(Knight::isFirstMove);
  }

  @Test
  void getPieceType() {
    assertThat(knight.getPieceType()).isEqualTo(Piece.PieceType.KNIGHT);
  }

  @Test
  void lShapedMove() {
    assertThat(knight.getMoveOffsets()).contains(LShaped.UP_UP_LEFT.getVector());
  }

  @Test
  void illegalMove() {
    assertThat(knight.getMoveOffsets()).doesNotContain(Vertical.UP.getVector());
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(destination);

    assertThat(knight.move(move))
        .isInstanceOf(Knight.class)
        .matches(knight -> knight.getPosition().equals(destination))
        .matches(knight -> !knight.isFirstMove());
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.vector.Diagonal;
import cl.vmardones.chess.engine.piece.vector.Horizontal;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RookTest {

  Rook rook;

  @Mock Coordinate coordinate;

  @Mock Coordinate destination;

  @Mock Move move;

  @BeforeEach
  void setUp() {
    rook = new Rook(coordinate, Alliance.BLACK);
  }

  @Test
  void constructor() {
    assertThat(new Rook(coordinate, Alliance.BLACK)).matches(Rook::isFirstMove);
  }

  @Test
  void getPieceType() {
    assertThat(rook.getPieceType()).isEqualTo(Piece.PieceType.ROOK);
  }

  @Test
  void horizontalMove() {
    assertThat(rook.getMoveVectors()).containsOnlyOnce(Horizontal.RIGHT.getVector());
  }

  @Test
  void verticalMove() {
    assertThat(rook.getMoveVectors()).containsOnlyOnce(Vertical.UP.getVector());
  }

  @Test
  void illegalMove() {
    assertThat(rook.getMoveVectors()).doesNotContain(Diagonal.DOWN_RIGHT.getVector());
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(destination);

    assertThat(rook.move(move))
        .isInstanceOf(Rook.class)
        .matches(rook -> rook.getPosition().equals(destination))
        .matches(rook -> !rook.isFirstMove());
  }
}

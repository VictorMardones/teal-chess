/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.vector.Diagonal;
import cl.vmardones.chess.engine.piece.vector.Horizontal;
import cl.vmardones.chess.engine.piece.vector.LShaped;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KingTest {

  King king;

  @Mock Coordinate coordinate;

  @Mock Coordinate destination;

  @Mock Move move;

  @BeforeEach
  void setUp() {
    king = new King(coordinate, Alliance.WHITE);
  }

  @Test
  void constructor() {
    assertThat(new King(coordinate, Alliance.BLACK)).matches(King::isFirstMove);
  }

  @Test
  void getPieceType() {
    assertThat(king.getPieceType()).isEqualTo(Piece.PieceType.KING);
  }

  @Test
  void diagonalMove() {
    assertThat(king.getMoveOffsets()).containsOnlyOnce(Diagonal.UP_RIGHT.getVector());
  }

  @Test
  void horizontalMove() {
    assertThat(king.getMoveOffsets()).containsOnlyOnce(Horizontal.LEFT.getVector());
  }

  @Test
  void verticalMove() {
    assertThat(king.getMoveOffsets()).containsOnlyOnce(Vertical.UP.getVector());
  }

  @Test
  void illegalMove() {
    assertThat(king.getMoveOffsets()).doesNotContain(LShaped.UP_UP_LEFT.getVector());
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(destination);

    assertThat(king.move(move))
        .isInstanceOf(King.class)
        .matches(king -> king.getPosition().equals(destination))
        .matches(king -> !king.isFirstMove());
  }
}

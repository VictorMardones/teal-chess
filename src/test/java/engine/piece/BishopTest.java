package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.board.Board;
import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BishopTest {

  Bishop bishop;
  @Mock Board board;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    bishop = new Bishop(Coordinate.of("a1"), Alliance.BLACK);
  }

  @Test
  void diagonalMove() {
    assertThat(bishop.isInMoveRange(board, Coordinate.of("a1"))).isTrue();
  }

  @Test
  void notInMoveRange() {
    assertThat(bishop.isInMoveRange(1)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(Coordinate.of("b2"));

    assertThat(bishop.move(move))
        .isInstanceOf(Bishop.class)
        .matches(bishop -> Objects.equals(bishop.getPosition(), Coordinate.of("b2")))
        .matches(bishop -> !bishop.isFirstMove());
  }
}

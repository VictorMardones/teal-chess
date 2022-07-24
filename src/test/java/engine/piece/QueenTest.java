package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.move.Move;
import engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueenTest {

  Queen queen;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    queen = new Queen(0, Alliance.BLACK);
  }

  @Test
  void diagonalMove() {
    assertThat(queen.isInMoveRange(63)).isTrue();
  }

  @Test
  void horizontalMove() {
    assertThat(queen.isInMoveRange(7)).isTrue();
  }

  @Test
  void verticalMove() {
    assertThat(queen.isInMoveRange(56)).isTrue();
  }

  @Test
  void notInMoveRange() {
    assertThat(queen.isInMoveRange(12)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(32);

    assertThat(queen.move(move))
        .isInstanceOf(Queen.class)
        .matches(queen -> queen.getPosition() == 32)
        .matches(queen -> !queen.isFirstMove());
  }
}

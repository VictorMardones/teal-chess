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
    when(boardComparator.sameColor(0, 63)).thenReturn(true);

    assertThat(queen.isInMoveRange(63)).isTrue();
  }

  @Test
  void horizontalMove() {
    when(boardComparator.sameRank(0, 7)).thenReturn(true);

    assertThat(queen.isInMoveRange(7)).isTrue();
  }

  @Test
  void verticalMove() {
    when(boardComparator.sameColumn(0, 56)).thenReturn(true);

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
    ;
  }
}

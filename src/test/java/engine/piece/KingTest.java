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
class KingTest {

  King king;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    king = new King(0, Alliance.WHITE);
  }

  @Test
  void isInMoveRange() {
    assertThat(king.isInMoveRange(1)).isTrue();
  }

  @Test
  void isNotInMoveRange() {
    assertThat(king.isInMoveRange(4)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(9);

    assertThat(king.move(move))
        .isInstanceOf(King.class)
        .matches(king -> king.getPosition() == 9)
        .matches(king -> !king.isFirstMove());
  }
}

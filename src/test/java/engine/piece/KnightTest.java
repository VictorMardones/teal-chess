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
class KnightTest {

  Knight knight;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    knight = new Knight(0, Alliance.WHITE);
  }

  @Test
  void isInMoveRange() {
    assertThat(knight.isInMoveRange(17)).isTrue();
  }

  @Test
  void isNotInMoveRange() {
    assertThat(knight.isInMoveRange(8)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(17);

    assertThat(knight.move(move))
        .isInstanceOf(Knight.class)
        .matches(knight -> knight.getPosition() == 17)
        .matches(knight -> !knight.isFirstMove());
    ;
  }
}

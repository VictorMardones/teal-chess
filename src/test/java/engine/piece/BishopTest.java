package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BishopTest {

  Bishop bishop;
  @Mock BoardService boardService;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    bishop = new Bishop(0, Alliance.BLACK, boardService);
  }

  @Test
  void diagonalMove() {
    when(boardService.sameColor(0, 9)).thenReturn(true);

    assertThat(bishop.isInMoveRange(9)).isTrue();
  }

  @Test
  void notInMoveRange() {
    assertThat(bishop.isInMoveRange(1)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(9);

    assertThat(bishop.move(move))
        .isInstanceOf(Bishop.class)
        .matches(bishop -> bishop.getPosition() == 9)
        .matches(bishop -> !bishop.isFirstMove());
  }
}

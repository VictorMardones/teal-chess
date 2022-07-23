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
class PawnTest {

  Pawn pawn;
  @Mock BoardService boardService;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    pawn = new Pawn(0, Alliance.WHITE, boardService);
  }

  @Test
  void isInMoveRange() {
    when(boardService.getColumn(0)).thenReturn(0);
    when(boardService.getColumn(8)).thenReturn(0);

    assertThat(pawn.isInMoveRange(8)).isTrue();
  }

  @Test
  void isNotInMoveRange() {
    when(boardService.getColumn(0)).thenReturn(0);
    when(boardService.getColumn(10)).thenReturn(2);

    assertThat(pawn.isInMoveRange(10)).isFalse();
  }

  @Test
  void move() {
    when(move.getDestination()).thenReturn(16);

    assertThat(pawn.move(move))
        .isInstanceOf(Pawn.class)
        .matches(pawn -> pawn.getPosition() == 16)
        .matches(pawn -> !pawn.isFirstMove());
    ;
  }
}

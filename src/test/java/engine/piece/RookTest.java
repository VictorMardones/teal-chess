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
class RookTest {

  Rook rook;
  @Mock BoardService boardService;
  @Mock Move move;

  @BeforeEach
  void setUp() {
    rook = new Rook(0, Alliance.BLACK, boardService);
  }

  @Test
  void horizontalMove() {
    when(boardService.sameRank(0, 7)).thenReturn(true);

    assertThat(rook.isInMoveRange(7)).isTrue();
  }

  @Test
  void verticalMove() {
    when(boardService.sameColumn(0, 56)).thenReturn(true);

    assertThat(rook.isInMoveRange(56)).isTrue();
  }

  @Test
  void notInMoveRange() {
    assertThat(rook.isInMoveRange(9)).isFalse();
  }

  @Test
  void movePiece() {
    when(move.getDestination()).thenReturn(5);

    assertThat(rook.move(move))
        .isInstanceOf(Rook.class)
        .matches(rook -> rook.getPosition() == 5)
        .matches(rook -> !rook.isFirstMove());
    ;
  }
}

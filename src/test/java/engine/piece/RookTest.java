package engine.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import engine.board.Coordinate;
import engine.move.Move;
import engine.player.Alliance;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RookTest {

  Rook rook;
  @Mock Coordinate coordinate;
  @Mock Move move;
  @Mock Coordinate destination;

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
    assertThat(Arrays.asList(rook.getMoveVectors()).contains(new int[] {0, 1})).isTrue();
  }

  @Test
  void verticalMove() {
    assertThat(Arrays.asList(rook.getMoveVectors()).contains(new int[] {1, 0})).isTrue();
  }

  @Test
  void illegalMove() {
    assertThat(Arrays.asList(rook.getMoveVectors()).contains(new int[] {1, 1})).isFalse();
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

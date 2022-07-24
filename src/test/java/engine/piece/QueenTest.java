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
class QueenTest {

    Queen queen;

    @Mock
    Coordinate coordinate;

    @Mock
    Move move;

    @Mock
    Coordinate destination;

    @BeforeEach
    void setUp() {
        queen = new Queen(coordinate, Alliance.BLACK);
    }

    @Test
    void constructor() {
        assertThat(new Queen(coordinate, Alliance.BLACK)).matches(Queen::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(queen.getPieceType()).isEqualTo(Piece.PieceType.QUEEN);
    }

    @Test
    void diagonalMove() {
        assertThat(Arrays.asList(queen.getMoveVectors()).contains(new int[] {1, 1}))
                .isTrue();
    }

    @Test
    void horizontalMove() {
        assertThat(Arrays.asList(queen.getMoveVectors()).contains(new int[] {0, 1}))
                .isTrue();
    }

    @Test
    void verticalMove() {
        assertThat(Arrays.asList(queen.getMoveVectors()).contains(new int[] {1, 0}))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(Arrays.asList(queen.getMoveVectors()).contains(new int[] {1, 2}))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(queen.move(move))
                .isInstanceOf(Queen.class)
                .matches(queen -> queen.getPosition().equals(destination))
                .matches(queen -> !queen.isFirstMove());
    }
}

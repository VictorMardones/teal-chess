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
class KingTest {

    King king;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        king = new King(coordinate, Alliance.WHITE);
    }

    @Test
    void constructor() {
        assertThat(new King(coordinate, Alliance.BLACK)).matches(King::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(king.getPieceType()).isEqualTo(Piece.PieceType.KING);
    }

    @Test
    void diagonalMove() {
        assertThat(Arrays.asList(king.getMoveOffsets()).contains(new int[] {1, 1}))
                .isTrue();
    }

    @Test
    void horizontalMove() {
        assertThat(Arrays.asList(king.getMoveOffsets()).contains(new int[] {0, 1}))
                .isTrue();
    }

    @Test
    void verticalMove() {
        assertThat(Arrays.asList(king.getMoveOffsets()).contains(new int[] {1, 0}))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(Arrays.asList(king.getMoveOffsets()).contains(new int[] {2, 2}))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(king.move(move))
                .isInstanceOf(King.class)
                .matches(king -> king.getPosition().equals(destination))
                .matches(king -> !king.isFirstMove());
    }
}

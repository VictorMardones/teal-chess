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
class BishopTest {

    Bishop bishop;

    Coordinate coordinate = Coordinate.of("c1");
    Coordinate destination = Coordinate.of("d1");

    @Mock
    Move move;

    @BeforeEach
    void setUp() {
        bishop = new Bishop(coordinate, Alliance.BLACK);
    }

    @Test
    void constructor() {
        assertThat(new Bishop(coordinate, Alliance.BLACK)).matches(Bishop::isFirstMove);
    }

    @Test
    void getPieceType() {
        assertThat(bishop.getPieceType()).isEqualTo(Piece.PieceType.BISHOP);
    }

    @Test
    void diagonalMove() {
        assertThat(Arrays.asList(bishop.getMoveVectors()).contains(new int[] {1, 1}))
                .isTrue();
    }

    @Test
    void illegalMove() {
        assertThat(Arrays.asList(bishop.getMoveVectors()).contains(new int[] {1, 0}))
                .isFalse();
    }

    @Test
    void move() {
        when(move.getDestination()).thenReturn(destination);

        assertThat(bishop.move(move))
                .isInstanceOf(Bishop.class)
                .matches(bishop -> bishop.getPosition().equals(destination))
                .matches(bishop -> !bishop.isFirstMove());
    }
}

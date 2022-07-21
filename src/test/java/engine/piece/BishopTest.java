package engine.piece;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BishopTest {

    @Test
    void diagonalMove() {
        var bishop = new Bishop(0, Alliance.BLACK);
        assertThat(bishop.isInMoveRange(9))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var bishop = new Bishop(0, Alliance.WHITE);
        assertThat(bishop.isInMoveRange(1))
                .isFalse();
    }
}
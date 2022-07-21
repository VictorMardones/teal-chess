package piece;

import engine.piece.Bishop;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BishopTest {

    @Test
    void diagonalMove() {
        var bishop = new Bishop(0, Alliance.BLACK);
        assertThat(bishop.isLegalMove(9))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var bishop = new Bishop(0, Alliance.WHITE);
        assertThat(bishop.isLegalMove(1))
                .isFalse();
    }
}
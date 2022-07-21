package engine.piece;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class RookTest {

    @ParameterizedTest
    // horizontal, vertical
    @ValueSource(ints = {7, 56})
    void legalMoves(int destination) {
        var rook = new Rook(0, Alliance.BLACK);
        assertThat(rook.isInMoveRange(destination))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertThat(rook.isInMoveRange(9))
                .isFalse();
    }
}
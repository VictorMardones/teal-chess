package engine.piece;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class QueenTest {

    @ParameterizedTest
    // diagonal, horizontal, vertical
    @ValueSource(ints = {63, 7, 56})
    void legalMoves(int destination) {
        var queen = new Queen(0, Alliance.BLACK);
        assertThat(queen.isInMoveRange(destination))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE);
        assertThat(queen.isInMoveRange(12))
                .isFalse();
    }
}
package piece;

import engine.piece.Queen;
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
        assertThat(queen.isLegalMove(destination))
                .isTrue();
    }

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE);
        assertThat(queen.isLegalMove(12))
                .isFalse();
    }
}
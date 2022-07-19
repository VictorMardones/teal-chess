package piece;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RookTest {

    @Test
    void illegalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertFalse(rook.isLegalMove(9));
    }

    @ParameterizedTest
    // horizontal, vertical
    @ValueSource(ints = {7, 56})
    void legalMoves(int destination) {
        var rook = new Rook(0, Alliance.BLACK);
        assertTrue(rook.isLegalMove(destination));
    }
}
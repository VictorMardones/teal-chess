package piece;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    void illegalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertTrue(rook.isIllegalMove(9));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 56}) // horizontal, vertical
    void legalMoves(int destination) {
        var rook = new Rook(0, Alliance.BLACK);
        assertFalse(rook.isIllegalMove(destination));
    }
}
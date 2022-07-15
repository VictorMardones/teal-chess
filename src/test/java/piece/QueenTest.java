package piece;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE);
        assertTrue(queen.isIllegalMove(12));
    }

    @ParameterizedTest
    @ValueSource(ints = {63, 7, 56}) // diagonal, horizontal, vertical
    void legalMoves(int destination) {
        var queen = new Queen(0, Alliance.BLACK);
        assertFalse(queen.isIllegalMove(destination));
    }
}
package piece;

import engine.piece.Queen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import engine.player.Alliance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueenTest {

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE);
        assertFalse(queen.isLegalMove(12));
    }

    @ParameterizedTest
    // diagonal, horizontal, vertical
    @ValueSource(ints = {63, 7, 56})
    void legalMoves(int destination) {
        var queen = new Queen(0, Alliance.BLACK);
        assertTrue(queen.isLegalMove(destination));
    }
}
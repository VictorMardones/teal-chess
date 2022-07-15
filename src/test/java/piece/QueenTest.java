package piece;

import org.junit.jupiter.api.Test;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void illegalMove() {
        var queen = new Queen(0, Alliance.WHITE);
        assertTrue(queen.isIllegalMove(12));
    }

    @Test
    void diagonalMove() {
        var queen = new Queen(0, Alliance.BLACK);
        assertFalse(queen.isIllegalMove(63));
    }

    @Test
    void horizontalMove() {
        var queen = new Queen(0, Alliance.BLACK);
        assertFalse(queen.isIllegalMove(32));
    }

    @Test
    void verticalMove() {
        var queen = new Queen(0, Alliance.BLACK);
        assertFalse(queen.isIllegalMove(5));
    }
}
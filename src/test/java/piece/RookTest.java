package piece;

import org.junit.jupiter.api.Test;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    void illegalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertTrue(rook.isIllegalMove(9));
    }

    @Test
    void horizontalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertFalse(rook.isIllegalMove(56));
    }

    @Test
    void verticalMove() {
        var rook = new Rook(0, Alliance.BLACK);
        assertFalse(rook.isIllegalMove(7));
    }
}
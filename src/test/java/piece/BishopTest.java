package piece;

import org.junit.jupiter.api.Test;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void illegalMove() {
        var bishop = new Bishop(0, Alliance.WHITE);
        assertTrue(bishop.isIllegalMove(1));
    }

    @Test
    void legalMove() {
        var bishop = new Bishop(0, Alliance.BLACK);
        assertFalse(bishop.isIllegalMove(9));
    }
}
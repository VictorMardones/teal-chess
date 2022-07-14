package board;

import org.junit.jupiter.api.Test;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class BoardUtilsTest {

    @Test
    void validposition() {
        assertTrue(BoardUtils.isValidCoordinate(15));
    }

    @Test
    void lowCoordinate() {
        assertFalse(BoardUtils.isValidCoordinate(-1));
    }

    @Test
    void highCoordinate() {
        assertFalse(BoardUtils.isValidCoordinate(64));
    }

    @Test
    void getWhiteTile() {
        assertEquals(Alliance.WHITE, BoardUtils.getTileColor(0));
    }

    @Test
    void getBlackTile() {
        assertEquals(Alliance.WHITE, BoardUtils.getTileColor(8));
    }
}
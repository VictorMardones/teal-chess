package board;

import engine.board.BoardUtils;
import org.junit.jupiter.api.Test;
import engine.player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class BoardUtilsTest {

    @Test
    void validCoordinate() {
        assertTrue(BoardUtils.isInsideBoard(15));
    }

    @Test
    void tooLowCoordinate() {
        assertFalse(BoardUtils.isInsideBoard(-1));
    }

    @Test
    void tooHighCoordinate() {
        assertFalse(BoardUtils.isInsideBoard(64));
    }

    @Test
    void getRow() {
        assertEquals(7, BoardUtils.getRow(61));
    }

    @Test
    void getColumn() {
        assertEquals(0, BoardUtils.getColumn(16));
    }

    @Test
    void sameRow() {
        assertTrue(BoardUtils.sameRow(3, 4));
    }

    @Test
    void differentRow() {
        assertFalse(BoardUtils.sameRow(1, 60));
    }

    @Test
    void sameColumn() {
        assertTrue(BoardUtils.sameColumn(8, 16));
    }

    @Test
    void differentColumn() {
        assertFalse(BoardUtils.sameColumn(8, 18));
    }

    @Test
    void getWhiteTile() {
        assertEquals(Alliance.WHITE, BoardUtils.getTileColor(0));
    }

    @Test
    void getBlackTile() {
        assertEquals(Alliance.BLACK, BoardUtils.getTileColor(1));
    }

    @Test
    void sameColor() {
        assertTrue(BoardUtils.sameColor(0, 63));
    }

    @Test
    void differentColor() {
        assertFalse(BoardUtils.sameColor(0, 7));
    }
}
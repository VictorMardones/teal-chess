package piece;

import org.junit.jupiter.api.Test;
import player.Alliance;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void isWhite() {
        var piece = new Pawn(0, Alliance.WHITE);
        assertTrue(piece.isWhite());
    }

    @Test
    void isNotWhite() {
        var piece = new Pawn(0, Alliance.BLACK);
        assertFalse(piece.isWhite());
    }

    @Test
    void isBlack() {
        var piece = new Pawn(0, Alliance.BLACK);
        assertTrue(piece.isBlack());
    }

    @Test
    void isNotBlack() {
        var piece = new Pawn(0, Alliance.WHITE);
        assertFalse(piece.isBlack());
    }
}
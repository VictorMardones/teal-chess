package engine.piece;

import engine.piece.Pawn;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PieceTest {

    @Test
    void isWhite() {
        var piece = new Pawn(0, Alliance.WHITE);
        assertThat(piece.isWhite())
                .isTrue();
    }

    @Test
    void isNotWhite() {
        var piece = new Pawn(0, Alliance.BLACK);
        assertThat(piece.isWhite())
                .isFalse();
    }

    @Test
    void isBlack() {
        var piece = new Pawn(0, Alliance.BLACK);
        assertThat(piece.isBlack())
                .isTrue();
    }

    @Test
    void isNotBlack() {
        var piece = new Pawn(0, Alliance.WHITE);
        assertThat(piece.isBlack())
                .isFalse();
    }
}
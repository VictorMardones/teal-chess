package engine.board;

import engine.board.BoardUtils;
import engine.player.Alliance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardUtilsTest {

    @Test
    void validCoordinate() {
        assertThat(BoardUtils.isInsideBoard(15))
                .isTrue();
    }

    @Test
    void tooLowCoordinate() {
        assertThat(BoardUtils.isInsideBoard(-1))
                .isFalse();
    }

    @Test
    void tooHighCoordinate() {
        assertThat(BoardUtils.isInsideBoard(64))
                .isFalse();
    }

    @Test
    void getRank() {
        assertThat(BoardUtils.getRank(61))
                .isEqualTo(1);
    }

    @Test
    void getColumn() {
        assertThat(BoardUtils.getColumn(11))
                .isEqualTo(3);
    }

    @Test
    void getColumnName() {
        assertThat(BoardUtils.getColumnName(16))
                .isEqualTo('a');
    }

    @Test
    void sameRank() {
        assertThat(BoardUtils.sameRank(3, 4))
                .isTrue();
    }

    @Test
    void differentRank() {
        assertThat(BoardUtils.sameRank(1, 60))
                .isFalse();
    }

    @Test
    void sameColumn() {
        assertThat(BoardUtils.sameColumn(8, 16))
                .isTrue();
    }

    @Test
    void differentColumn() {
        assertThat(BoardUtils.sameColumn(8, 18))
                .isFalse();
    }

    @Test
    void getWhiteTile() {
        assertThat(BoardUtils.getTileColor(0))
                .isEqualTo(Alliance.WHITE);
    }

    @Test
    void getBlackTile() {
        assertThat(BoardUtils.getTileColor(1))
                .isEqualTo(Alliance.BLACK);
    }

    @Test
    void sameColor() {
        assertThat(BoardUtils.sameColor(0, 63))
                .isTrue();
    }

    @Test
    void differentColor() {
        assertThat(BoardUtils.sameColor(0, 7))
                .isFalse();
    }
}
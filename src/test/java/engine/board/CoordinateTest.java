package engine.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;

public class CoordinateTest {

    @Test
    void invalidIndex() {
        assertThatThrownBy(() -> Coordinate.of(-1))
                .isInstanceOf(InvalidCoordinateException.class)
                .hasMessageContaining("-1");
    }

    @Test
    void invalidAlgebraicNotation() {
        assertThatThrownBy(() -> Coordinate.of("x4"))
                .isInstanceOf(InvalidCoordinateException.class)
                .hasMessageContaining("x4");
    }

    @Test
    void getRank() {
        assertThat(Coordinate.of("f5").getRank()).isEqualTo(5);
    }

    @Test
    void sameRank() {
        assertThat(Coordinate.of("c1").sameRankAs(Coordinate.of("d1"))).isTrue();
    }

    @Test
    void differentRank() {
        assertThat(Coordinate.of("a4").sameRankAs(Coordinate.of("a5"))).isFalse();
    }

    @Test
    void getColumn() {
        assertThat(Coordinate.of("f5").getColumn()).isEqualTo('f');
    }

    @Test
    void sameColumn() {
        assertThat(Coordinate.of("g7").sameColumnAs(Coordinate.of("g2"))).isTrue();
    }

    @Test
    void differentColumn() {
        assertThat(Coordinate.of("e1").sameColumnAs(Coordinate.of("f1"))).isFalse();
    }

    @Test
    void getWhiteColor() {
        assertThat(Coordinate.of("a8").getColor()).isEqualTo(Alliance.WHITE);
    }

    @Test
    void getBlackColor() {
        assertThat(Coordinate.of("b8").getColor()).isEqualTo(Alliance.BLACK);
    }

    @Test
    void sameColor() {
        assertThat(Coordinate.of("a8").sameColorAs(Coordinate.of("h1"))).isTrue();
    }

    @Test
    void differentColor() {
        assertThat(Coordinate.of("a8").sameColorAs(Coordinate.of("h8"))).isFalse();
    }

    @Test
    void to() {
        assertThat(Coordinate.of("b2").to(1, 1).isPresent()).isTrue();
        assertThat(Coordinate.of("b2").to(1, 1).get()).isEqualTo(Coordinate.of("c3"));
    }

    @Test
    void toOutside() {
        assertThat(Coordinate.of("b1").to(-2, 1).isPresent()).isFalse();
    }

    @Test
    void up() {
        assertThat(Coordinate.of("a1").up(2).isPresent()).isTrue();
        assertThat(Coordinate.of("a1").up(2).get()).isEqualTo(Coordinate.of("a3"));
    }

    @Test
    void upOutside() {
        assertThat(Coordinate.of("c8").up(1).isEmpty()).isTrue();
    }

    @Test
    void down() {
        assertThat(Coordinate.of("e4").down(2).isPresent()).isTrue();
        assertThat(Coordinate.of("e4").down(2).get()).isEqualTo(Coordinate.of("e2"));
    }

    @Test
    void downOutside() {
        assertThat(Coordinate.of("g1").down(1).isEmpty()).isTrue();
    }

    @Test
    void left() {
        assertThat(Coordinate.of("e5").left(2).isPresent()).isTrue();
        assertThat(Coordinate.of("e5").left(2).get()).isEqualTo(Coordinate.of("c5"));
    }

    @Test
    void leftOutside() {
        assertThat(Coordinate.of("a6").left(1).isEmpty()).isTrue();
    }

    @Test
    void right() {
        assertThat(Coordinate.of("b5").up(2).isPresent()).isTrue();
        assertThat(Coordinate.of("b5").up(2).get()).isEqualTo(Coordinate.of("b7"));
    }

    @Test
    void rightOutside() {
        assertThat(Coordinate.of("h3").right(1).isEmpty()).isTrue();
    }
}

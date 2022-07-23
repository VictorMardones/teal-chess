package engine.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;

public class CoordinateTest {

  @Test
  void invalidIndex() {
    assertThatThrownBy(() -> new Coordinate(-1))
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
  void up() {
    assertThat(Coordinate.of("a1").up(2))
            .isEqualTo(Coordinate.of("c1"));
  }

  @Test
  void upOutside(){
    assertThatThrownBy(() -> Coordinate.of("a8").up(1))
            .isInstanceOf(InvalidCoordinateException.class);
  }

  @Test
  void down() {
    assertThat(Coordinate.of("e1").down(2))
            .isEqualTo(Coordinate.of("c1"));
  }

  @Test
  void downOutside() {
    assertThatThrownBy(() -> Coordinate.of("a1").down(1))
            .isInstanceOf(InvalidCoordinateException.class);
  }

  @Test
  void left() {
    assertThat(Coordinate.of("e5").up(2))
            .isEqualTo(Coordinate.of("e3"));
  }

  @Test
  void leftOutside() {
    assertThatThrownBy(() -> Coordinate.of("a1").left(1))
            .isInstanceOf(InvalidCoordinateException.class);
  }

  @Test
  void right() {
    assertThat(Coordinate.of("e5").up(2))
            .isEqualTo(Coordinate.of("e7"));
  }

  @Test
  void rightOutside() {
    assertThatThrownBy(() -> Coordinate.of("a8").right(1))
            .isInstanceOf(InvalidCoordinateException.class);
  }
}

package engine.board;

import static org.assertj.core.api.Assertions.assertThat;

import engine.player.Alliance;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

  BoardService boardService = new BoardService();

  @Test
  void isInside() {
    assertThat(boardService.isInside(15)).isTrue();
  }

  @Test
  void isOutside() {
    assertThat(boardService.isInside(-1)).isFalse();
    assertThat(boardService.isInside(64)).isFalse();
  }

  @Test
  void getRank() {
    assertThat(boardService.getRank(61)).isEqualTo(1);
  }

  @Test
  void getColumn() {
    assertThat(boardService.getColumn(11)).isEqualTo(3);
  }

  @Test
  void getColumnName() {
    assertThat(boardService.getColumnName(16)).isEqualTo('a');
  }

  @Test
  void sameRank() {
    assertThat(boardService.sameRank(3, 4)).isTrue();
  }

  @Test
  void differentRank() {
    assertThat(boardService.sameRank(1, 60)).isFalse();
  }

  @Test
  void sameColumn() {
    assertThat(boardService.sameColumn(8, 16)).isTrue();
  }

  @Test
  void differentColumn() {
    assertThat(boardService.sameColumn(8, 18)).isFalse();
  }

  @Test
  void getWhiteTile() {
    assertThat(boardService.getTileColor(0)).isEqualTo(Alliance.WHITE);
  }

  @Test
  void getBlackTile() {
    assertThat(boardService.getTileColor(1)).isEqualTo(Alliance.BLACK);
  }

  @Test
  void sameColor() {
    assertThat(boardService.sameColor(0, 63)).isTrue();
  }

  @Test
  void differentColor() {
    assertThat(boardService.sameColor(0, 7)).isFalse();
  }
}

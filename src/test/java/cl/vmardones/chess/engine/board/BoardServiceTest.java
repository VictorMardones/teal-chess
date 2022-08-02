/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardTest {

  Board board = Board.createStandardBoard();

  @Test
  void toText() {
    assertThat(board.toText())
        .containsOnlyOnce("r  n  b  q  k  b  n  r")
        .containsOnlyOnce("p  p  p  p  p  p  p  p")
        .contains("-  -  -  -  -  -  -  -")
        .containsOnlyOnce("P  P  P  P  P  P  P  P")
        .containsOnlyOnce("R  N  B  Q  K  B  N  R")
        .contains("\n");
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

  BoardService boardService;

  @BeforeEach
  void setUp() {
    boardService = new BoardService();
  }

  @Test
  void prettyPrint() {
    var board = boardService.createStandardBoard();

    assertThat(boardService.prettyPrint(board))
        .containsOnlyOnce("r  n  b  q  k  b  n  r")
        .containsOnlyOnce("p  p  p  p  p  p  p  p")
        .contains("-  -  -  -  -  -  -  -")
        .containsOnlyOnce("P  P  P  P  P  P  P  P")
        .containsOnlyOnce("R  N  B  Q  K  B  N  R")
        .contains("\n");
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.board;

import static org.assertj.core.api.Assertions.assertThat;

import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.Piece;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

  BoardService boardService;
  Collection<Piece> pieces;

  @Mock Board board;
  @Mock Piece piece1;
  @Mock Piece piece2;
  @Mock Move legalMove1;
  @Mock Move legalMove2;

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

  @Test
  void calculateLegals() {
    pieces = List.of(piece1, piece2);

    Mockito.when(piece1.calculateLegals(board)).thenReturn(List.of(legalMove1));
    Mockito.when(piece2.calculateLegals(board)).thenReturn(List.of(legalMove2));

    assertThat(boardService.calculateLegals(board, pieces))
        .containsOnlyOnce(legalMove1)
        .containsOnlyOnce(legalMove2);
  }
}

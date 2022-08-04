/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.engine.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TurnTest {

  Turn whiteTurn;
  Turn blackTurn;

  @Mock Board board;
  @Mock Player whitePlayer;
  @Mock Player blackPlayer;

  @BeforeEach
  void setUp() {
    whiteTurn = new Turn(board, Alliance.WHITE, whitePlayer, blackPlayer);
    blackTurn = new Turn(board, Alliance.BLACK, whitePlayer, blackPlayer);
  }

  @Test
  void getWhitePlayer() {
    assertThat(whiteTurn.getPlayer()).isEqualTo(whitePlayer);
  }

  @Test
  void getBlackPlayer() {
    assertThat(blackTurn.getPlayer()).isEqualTo(blackPlayer);
  }

  @Test
  void getWhiteOpponent() {
    assertThat(whiteTurn.getOpponent()).isEqualTo(blackPlayer);
  }

  @Test
  void getBlackOpponent() {
    assertThat(blackTurn.getOpponent()).isEqualTo(whitePlayer);
  }
}

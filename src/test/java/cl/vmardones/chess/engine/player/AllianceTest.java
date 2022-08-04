/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllianceTest {

  Alliance white = Alliance.WHITE;

  Alliance black = Alliance.BLACK;

  List<Player> players;

  @Mock Player whitePlayer;

  @Mock Player blackPlayer;

  @Test
  void getWhiteOpposite() {
    assertThat(white.getOpposite()).isEqualTo(black);
  }

  @Test
  void getBlackOpposite() {
    assertThat(black.getOpposite()).isEqualTo(white);
  }

  @Test
  void getWhiteDirection() {
    assertThat(white.getDirection()).isPositive().isEqualTo(1);
  }

  @Test
  void getBlackDirection() {
    assertThat(black.getDirection()).isNegative().isEqualTo(-1);
  }

  @Test
  void getWhiteOppositeDirection() {
    assertThat(white.getOppositeDirection()).isNegative().isEqualTo(-1);
  }

  @Test
  void getBlackOppositeDirection() {
    assertThat(black.getOppositeDirection()).isPositive().isEqualTo(1);
  }

  @Test
  void chooseWhitePlayer() {
    when(whitePlayer.getAlliance()).thenReturn(white);
    when(blackPlayer.getAlliance()).thenReturn(black);

    players = List.of(blackPlayer, whitePlayer);

    assertThat(white.choosePlayer(players)).isEqualTo(whitePlayer);
  }

  @Test
  void chooseBlackPlayer() {
    when(whitePlayer.getAlliance()).thenReturn(white);
    when(blackPlayer.getAlliance()).thenReturn(black);

    players = List.of(whitePlayer, blackPlayer);

    assertThat(black.choosePlayer(players)).isEqualTo(blackPlayer);
  }
}

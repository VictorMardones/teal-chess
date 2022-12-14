/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.move;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MoveStatusTest {

  @Test
  void isDone() {
    assertThat(MoveStatus.DONE.isDone()).isTrue();
  }

  @Test
  void isNotDone() {
    assertThat(MoveStatus.NULL.isDone()).isFalse();
    assertThat(MoveStatus.LEAVES_OPPONENT_IN_CHECK.isDone()).isFalse();
    assertThat(MoveStatus.ILLEGAL.isDone()).isFalse();
  }
}

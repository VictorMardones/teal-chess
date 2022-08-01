/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece.vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LShaped implements Vector {
  UP_UP_LEFT(new int[] {-1, 2}),
  UP_UP_RIGHT(new int[] {1, 2}),
  UP_LEFT_LEFT(new int[] {-2, 1}),
  UP_RIGHT_RIGHT(new int[] {2, 1}),
  DOWN_DOWN_LEFT(new int[] {-1, -2}),
  DOWN_DOWN_RIGHT(new int[] {1, -2}),
  DOWN_LEFT_LEFT(new int[] {-2, -1}),
  DOWN_RIGHT_RIGHT(new int[] {2, -1});

  private final int[] vector;
}

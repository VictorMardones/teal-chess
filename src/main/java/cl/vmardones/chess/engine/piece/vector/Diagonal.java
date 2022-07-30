/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece.vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Diagonal implements Vector {
    UP_LEFT(new int[] {-1, 1}),
    UP_RIGHT(new int[] {1, 1}),
    DOWN_LEFT(new int[] {-1, -1}),
    DOWN_RIGHT(new int[] {1, -1});

    private final int[] vector;
}

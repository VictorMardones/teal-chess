/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece.vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Jump implements Vector {
    UP(new int[] {0, 2}),
    DOWN(new int[] {0, -2});

    private final int[] vector;
}

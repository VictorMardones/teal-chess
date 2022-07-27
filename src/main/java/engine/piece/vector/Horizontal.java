/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.piece.vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Horizontal implements Vector {
    LEFT(new int[] {-1, 0}),
    RIGHT(new int[] {1, 0});

    private final int[] vector;
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.move;

import engine.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** The transition from one board to another, triggered when a move is performed or checked. */
@AllArgsConstructor
public class MoveTransition {

    @Getter
    private final Board board;

    private final Move move;

    @Getter
    private final MoveStatus moveStatus;
}

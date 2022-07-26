/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Pawn;
import lombok.EqualsAndHashCode;

/**
 * A move where a pawn gets to another tile.
 */
@EqualsAndHashCode(callSuper = true)
public class PawnMove extends Move {
    public PawnMove(Board board, Pawn pawn, Coordinate destination) {
        super(board, pawn, destination);
    }
}

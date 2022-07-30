/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Pawn;
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

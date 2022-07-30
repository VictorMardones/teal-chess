/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import lombok.EqualsAndHashCode;

/**
 * A move where a non-pawn piece gets to another tile.
 */
@EqualsAndHashCode(callSuper = true)
public class MajorMove extends Move {
    public MajorMove(Board board, Piece piece, Coordinate destination) {
        super(board, piece, destination);
    }
}

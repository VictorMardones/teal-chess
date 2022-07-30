/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.piece.Rook;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class KingSideCastleMove extends CastleMove {

    public KingSideCastleMove(Board board, Piece piece, Coordinate destination, Rook rook, Coordinate rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0";
    }
}

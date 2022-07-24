/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;
import engine.piece.Rook;

public class KingSideCastleMove extends CastleMove {

    public KingSideCastleMove(Board board, Piece piece, Coordinate destination, Rook rook, Coordinate rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0";
    }
}

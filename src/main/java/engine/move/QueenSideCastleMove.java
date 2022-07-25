/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;
import engine.piece.Rook;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class QueenSideCastleMove extends CastleMove {

    public QueenSideCastleMove(
            Board board, Piece piece, Coordinate destination, Rook rook, Coordinate rookDestination) {
        super(board, piece, destination, rook, rookDestination);
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
}

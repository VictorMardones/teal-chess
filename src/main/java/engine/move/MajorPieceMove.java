/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */
package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;

/** A move where a non-pawn piece gets to another tile. */
public class MajorPieceMove extends Move {
    public MajorPieceMove(Board board, Piece piece, Coordinate destination) {
        super(board, piece, destination);
    }

    @Override
    public String toString() {
        return piece.getPieceType().toString() + getDestination();
    }
}

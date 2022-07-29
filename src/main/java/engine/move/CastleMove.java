/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Piece;
import engine.piece.Rook;

abstract class CastleMove extends Move {
    protected final Rook rook;
    protected final Coordinate rookDestination;

    protected CastleMove(Board board, Piece piece, Coordinate destination, Rook rook, Coordinate rookDestination) {
        super(board, piece, destination);
        this.rook = rook;
        this.rookDestination = rookDestination;
        castling = true;
    }

    @Override
    public Board execute() {
        return board.nextTurnBuilder()
                .withoutPiece(piece)
                .withoutPiece(rook)
                .piece(piece.move(this))
                .piece(getMovedRook())
                .build();
    }

    private Rook getMovedRook() {
        return new Rook(rookDestination, rook.getAlliance(), false);
    }
}

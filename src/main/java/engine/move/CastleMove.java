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
        final var builder = Board.builder();

        builder.whiteKing(board.getWhitePlayer().getKing())
                .blackKing(board.getBlackPlayer().getKing());

        board.getCurrentPlayer().getActivePieces().stream()
                .filter(activePiece -> !piece.equals(activePiece) && !piece.equals(rook))
                .forEach(builder::piece);

        board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::piece);

        builder.piece(piece.move(this))
                .piece(new Rook(rookDestination, rook.getAlliance(), false))
                .moveMaker(board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Pawn;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PawnJump extends Move {
    public PawnJump(Board board, Pawn pawn, Coordinate destination) {
        super(board, pawn, destination);
    }

    @Override
    public Board execute() {
        final var builder = Board.builder();

        builder.whiteKing(board.getWhitePlayer().getKing())
                .blackKing(board.getBlackPlayer().getKing());

        board.getCurrentPlayer().getActivePieces().stream()
                .filter(activePiece -> !piece.equals(activePiece))
                .forEach(builder::piece);

        board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::piece);

        final var movedPawn = piece.move(this);

        builder.piece(movedPawn)
                .enPassantPawn((Pawn) movedPawn)
                .moveMaker(board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}

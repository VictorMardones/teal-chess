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
        return board.nextTurnBuilder()
                .withoutPiece(piece)
                .piece(piece.move(this))
                .enPassantPawn((Pawn) piece.move(this))
                .build();
    }
}

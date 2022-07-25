/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Pawn;
import engine.piece.Piece;
import lombok.EqualsAndHashCode;

/** A move where a pawn captures another piece. */
@EqualsAndHashCode(callSuper = true)
public class PawnCaptureMove extends CaptureMove {
    public PawnCaptureMove(Board board, Pawn pawn, Coordinate destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }
}

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
import lombok.extern.slf4j.Slf4j;

// TODO: Fix implementation, highlighted moves don't match moves that are executed after clicking
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class EnPassantMove extends CaptureMove {
    public EnPassantMove(Board board, Pawn pawn, Coordinate destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }
}

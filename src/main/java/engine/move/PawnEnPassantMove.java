/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.move;

import engine.board.Board;
import engine.board.Coordinate;
import engine.piece.Pawn;
import engine.piece.Piece;

public class PawnEnPassantMove extends PawnCaptureMove {
    public PawnEnPassantMove(Board board, Pawn pawn, Coordinate destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }
}

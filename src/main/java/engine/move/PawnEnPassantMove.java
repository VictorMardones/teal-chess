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

@EqualsAndHashCode(callSuper = true)
@Slf4j
public class PawnEnPassantMove extends PawnCaptureMove {
    public PawnEnPassantMove(Board board, Pawn pawn, Coordinate destination, Piece capturedPiece) {
        super(board, pawn, destination, capturedPiece);
    }

    @Override
    public Board execute() {
        final var builder = new Board.Builder(
                board.getWhitePlayer().getKing(), board.getBlackPlayer().getKing());

        board.getCurrentPlayer().getActivePieces().stream()
                .filter(activePiece -> !piece.equals(activePiece))
                .forEach(builder::withPiece);

        board.getCurrentPlayer().getOpponent().getActivePieces().stream().filter(activePiece -> !capturedPiece.equals(activePiece)).forEach(builder::withPiece);

        log.info("Moving the selected piece to {}", piece.move(this));

        builder.withPiece(piece.move(this));

        builder.withMoveMaker(board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}

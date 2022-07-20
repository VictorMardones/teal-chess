package move;

import board.Board;
import piece.Pawn;

public final class PawnJump extends Move {
    public PawnJump(Board board, Pawn pawn, int destination) {
        super(board, pawn, destination);
    }

    @Override
    public Board execute() {

        final var builder = new Board.Builder(board.getWhitePlayer().getKing(),
                board.getBlackPlayer().getKing());

        for (final var activePiece : board.getCurrentPlayer().getActivePieces()) {
            if (!piece.equals(activePiece)) {
                builder.withPiece(activePiece);
            }
        }

        for (final var activePiece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.withPiece(activePiece);
        }

        final var movedPawn = piece.movePiece(this);

        builder.withPiece(movedPawn);
        builder.withEnPassantPawn((Pawn) movedPawn);
        builder.withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}

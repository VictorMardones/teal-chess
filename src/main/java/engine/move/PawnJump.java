package engine.move;

import engine.board.Board;
import engine.piece.Pawn;

public final class PawnJump extends Move {
    public PawnJump(Board board, Pawn pawn, int destination) {
        super(board, pawn, destination);
    }

    @Override
    public Board execute() {

        final var builder = new Board.Builder(board.getWhitePlayer().getKing(), board.getBlackPlayer().getKing());

        board.getCurrentPlayer().getActivePieces().stream()
                .filter(activePiece -> !piece.equals(activePiece))
                .forEach(builder::withPiece);

        board.getCurrentPlayer().getOpponent().getActivePieces()
                .forEach(builder::withPiece);

        final var movedPawn = piece.move(this);

        builder.withPiece(movedPawn);
        builder.withEnPassantPawn((Pawn) movedPawn);
        builder.withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }
}

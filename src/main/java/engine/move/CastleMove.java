package engine.move;

import engine.board.Board;
import engine.piece.Piece;
import engine.piece.Rook;

abstract class CastleMove extends Move {
    protected final Rook rook;
    protected final int rookDestination;

    public CastleMove(Board board, Piece piece, int destination, Rook rook, int rookDestination) {
        super(board, piece, destination);
        this.rook = rook;
        this.rookDestination = rookDestination;
        castling = true;
    }

    @Override
    public Board execute() {
        final var builder = new Board.Builder();

        for (final var activePiece : board.getCurrentPlayer().getActivePieces()) {
            if (!piece.equals(activePiece) && !piece.equals(rook)) {
                builder.withPiece(activePiece);
            }
        }

        for (final var activePiece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.withPiece(activePiece);
        }

        builder.withPiece(piece.movePiece(this))
                .withPiece(new Rook(rookDestination, rook.getAlliance()))
                .withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
}

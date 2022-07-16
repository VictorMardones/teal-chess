package board;

import board.Board.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import piece.Piece;

/**
 * The action of moving a piece.
 */
@AllArgsConstructor
public abstract class Move {

    private Board board;
    @Getter
    private final Piece piece;
    @Getter
    private final int destination;

    /**
     * When a move is performed, a new board is created, because the board class is immutable.
     * @return The new board, after the move was performed
     */
    public Board execute() {

        final Builder builder = new Builder();

        for (final var piece : board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(piece)) {
                builder.withPiece(piece);
            }
        }

        for (final var piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.withPiece(piece);
        }

        builder.withPiece(piece.movePiece(this));
        builder.withNextTurn(board.getCurrentPlayer().getAlliance());
        return builder.build();
    }

    /**
     * A move where a piece gets to another tile.
     */
    public static final class NormalMove extends Move {
        public NormalMove(Board board, Piece piece, int destination) {
            super(board, piece, destination);
        }
    }

    /**
     * A move where a piece captures the piece in another tile.
     */
    public static final class CaptureMove extends Move {

        final Piece attackedPiece;

        public CaptureMove(Board board, Piece piece, int destination, Piece attackedPiece) {
            super(board, piece, destination);
            this.attackedPiece = attackedPiece;
        }
    }
}

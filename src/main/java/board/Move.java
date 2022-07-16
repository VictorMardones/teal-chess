package board;

import board.Board.Builder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import piece.*;

/**
 * The action of moving a piece.
 */
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Move {

    private static final Move NULL_MOVE = new NullMove();

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

    public int getCurrentCoordinate() {
        return piece.getPosition();
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
     * A move where a piece captures another piece.
     */
    public static class CaptureMove extends Move {

        final Piece attackedPiece;

        public CaptureMove(Board board, Piece piece, int destination, Piece attackedPiece) {
            super(board, piece, destination);
            this.attackedPiece = attackedPiece;
        }
    }

    /**
     * A move where a pawn gets to another tile.
     */
    public static final class PawnMove extends Move {
        public PawnMove(Board board, Pawn pawn, int destination) {
            super(board, pawn, destination);
        }
    }

    /**
     * A move where a pawn captures another piece.
     */
    public static class PawnCaptureMove extends CaptureMove {
        public PawnCaptureMove(Board board, Pawn pawn, int destination, Piece attackedPiece) {
            super(board, pawn, destination, attackedPiece);
        }
    }

    public static final class PawnEnPassantMove extends PawnCaptureMove {
        public PawnEnPassantMove(Board board, Pawn pawn, int destination, Piece attackedPiece) {
            super(board, pawn, destination, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {
        public PawnJump(Board board, Pawn pawn, int destination) {
            super(board, pawn, destination);
        }
    }

    public static abstract class CastleMove extends Move {
        public CastleMove(Board board, Piece piece, int destination) {
            super(board, piece, destination);
        }
    }

    public static abstract class KingCastleMove extends Move {
        public KingCastleMove(Board board, King king, int destination) {
            super(board, king, destination);
        }
    }

    public static abstract class QueenCastleMove extends Move {
        public QueenCastleMove(Board board, Queen queen, int destination) {
            super(board, queen, destination);
        }
    }

    public static abstract class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new IllegalStateException("Cannot execute an impossible move!");
        }
    }

    public static final class MoveFactory {

        private MoveFactory() {
            throw new IllegalStateException("You cannot instantiate me!");
        }

        public static Move create(final Board board, final int origin, final int destination) {
            return board.getCurrentPlayerLegalMoves()
                    .stream()
                    .filter(move -> move.piece.getPosition() == origin && move.destination == destination)
                    .findFirst()
                    .orElse(NULL_MOVE);
        }
    }
}

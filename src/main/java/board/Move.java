package board;

import board.Board.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import piece.*;

/**
 * The action of moving a piece.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class Move {

    protected final Board board;
    @Getter
    protected final Piece piece;
    @Getter
    private final int destination;

    @Getter
    protected boolean castling = false;
    @Getter
    protected Piece capturedPiece;

    public boolean isCapturing() {
        return capturedPiece != null;
    }

    /**
     * When a move is performed, a new board is created, because the board class is immutable.
     * @return The new board, after the move was performed
     */
    public Board execute() {

        final var builder = new Builder();

        for (final var activePiece : board.getCurrentPlayer().getActivePieces()) {
            if (!piece.equals(activePiece)) {
                builder.withPiece(activePiece);
            }
        }

        for (final var activePiece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.withPiece(activePiece);
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
    @EqualsAndHashCode(callSuper = true)
    public static class CaptureMove extends Move {

        public CaptureMove(Board board, Piece piece, int destination, Piece capturedPiece) {
            super(board, piece, destination);

            this.capturedPiece = capturedPiece;
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
        public PawnCaptureMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
            super(board, pawn, destination, capturedPiece);
        }
    }

    public static final class PawnEnPassantMove extends PawnCaptureMove {
        public PawnEnPassantMove(Board board, Pawn pawn, int destination, Piece capturedPiece) {
            super(board, pawn, destination, capturedPiece);
        }
    }

    public static final class PawnJump extends Move {
        public PawnJump(Board board, Pawn pawn, int destination) {
            super(board, pawn, destination);
        }

        @Override
        public Board execute() {

            final var builder = new Builder();

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

    static abstract class CastleMove extends Move {
        @Getter
        protected final Rook rook;
        protected final int start;
        protected final int rookDestination;

        public CastleMove(Board board, Piece piece, int destination, Rook rook, int start, int rookDestination) {
            super(board, piece, destination);
            this.rook = rook;
            this.start = start;
            this.rookDestination = rookDestination;
            castling = true;
        }

        @Override
        public Board execute() {
            final var builder = new Builder();

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
        }
    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(Board board, Piece piece, int destination, Rook rook, int start, int rookDestination) {
            super(board, piece, destination, rook, start, rookDestination);
        }

        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(Board board, Piece piece, int destination, Rook rook, int start, int rookDestination) {
            super(board, piece, destination, rook, start, rookDestination);
        }

        @Override
        public String toString() {
            return "0-0-0";
        }
    }

    public static class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new IllegalStateException("Cannot execute an impossible move!");
        }
    }

    public static final class MoveFactory {

        private static final Move NULL_MOVE = new NullMove();

        private MoveFactory() {
            throw new IllegalStateException("You cannot instantiate me!");
        }

        public static Move create(final Board board, final int source, final int destination) {
            return board.getCurrentPlayerLegalMoves()
                    .stream()
                    .filter(move -> move.piece.getPosition() == source && move.destination == destination)
                    .findFirst()
                    .orElse(NULL_MOVE);
        }
    }
}

package engine.move;

import engine.board.Board;
import engine.board.Board.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import engine.piece.Piece;
import lombok.ToString;

/**
 * The action of moving a piece.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public abstract class Move {

    @ToString.Exclude
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
     *
     * @return The new board, after the move was performed
     */
    public Board execute() {

        final var builder = new Builder(board.getWhitePlayer().getKing(), board.getBlackPlayer().getKing());

        for (final var activePiece : board.getCurrentPlayer().getActivePieces()) {
            if (!piece.equals(activePiece)) {
                builder.withPiece(activePiece);
            }
        }

        for (final var activePiece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.withPiece(activePiece);
        }

        builder.withPiece(piece.movePiece(this));
        builder.withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public int getCurrentCoordinate() {
        return piece.getPosition();
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

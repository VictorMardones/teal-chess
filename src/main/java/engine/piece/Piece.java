package engine.piece;

import engine.board.Board;
import engine.board.Tile;
import engine.move.CaptureMove;
import engine.move.MajorPieceMove;
import engine.move.Move;
import engine.player.Alliance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;

/**
 * A chess piece.
 */
public interface Piece {

    int getPosition();

    Alliance getAlliance();

    PieceType getPieceType();

    // TODO: Actually use this method
    default boolean isFirstMove() {
        return true;
    }

    /**
     * Calculates all the moves that a piece can do.
     *
     * @param board Current state of the game board.
     * @return List of possible moves.
     */
    Collection<Move> calculateLegalMoves(final Board board);

    /**
     * Checks for edge cases to decide if the next move could be valid.
     * This doesn't account for pieces that may block the movement.
     *
     * @param destination Destination coordinate.
     * @return True if the next move is valid.
     */
    boolean isInMoveRange(final int destination);

    default boolean isWhite() {
        return getAlliance() == Alliance.WHITE;
    }

    default boolean isBlack() {
        return !isWhite();
    }

    default boolean isEnemy(Piece other) {
        if (other != null) {
            return getAlliance() != other.getAlliance();
        }
        return false;
    }

    default String toChar() {
        return getPieceType().pieceName;
    }

    Piece movePiece(final Move move);

    default boolean isRook() {
        return getPieceType() == PieceType.ROOK;
    }

    /**
     * Checks if the given piece can get the destination.
     * This happens only if the destination is free or has a piece that can be captured.
     *
     * @param piece       The piece we're currently using.
     * @param destination The target destination.
     * @return True if the piece can get to the destination.
     */
    default boolean isAccessible(final Piece piece, final Tile destination) {
        final var pieceAtDestination = destination.getPiece();
        return pieceAtDestination.isEmpty() || piece.isEnemy(pieceAtDestination.get());
    }

    /**
     * Creates a move, based on the piece and the destination.
     *
     * @param piece       The piece we're moving.
     * @param destination The destination tile.
     * @param board       The current game board.
     * @return A move, selected depending on the source and destination.
     */
    default Optional<Move> createMove(final Piece piece, final Tile destination, final Board board) {
        if (destination.getPiece().isEmpty()) {
            return Optional.of(new MajorPieceMove(board, piece, destination.getCoordinate()));
        }

        final var capturablePiece = destination.getPiece();

        if (capturablePiece.isPresent() && piece.isEnemy(capturablePiece.get())) {
            return Optional.of(new CaptureMove(board, piece, destination.getCoordinate(), capturablePiece.get()));
        }

        return Optional.empty();
    }

    @AllArgsConstructor
    @Getter
    enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;
    }
}

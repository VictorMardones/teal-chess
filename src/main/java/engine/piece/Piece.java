package engine.piece;

import engine.board.Board;
import engine.move.Move;
import engine.player.Alliance;
import lombok.*;

import java.util.Collection;

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
        // TODO: Replace null by EmptyPiece
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

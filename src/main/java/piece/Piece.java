package piece;

import board.Board;
import board.Move;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import player.Alliance;

import java.util.Collection;

/**
 * A chess piece.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Piece {

    protected final int position;
    protected final Alliance alliance;
    protected final PieceType pieceType;
    // TODO: Actually use this field
    protected boolean firstMove;

    /**
     * Calculates all the moves that a piece can do.
     *
     * @param board Current state of the game board.
     * @return List of possible moves.
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    /**
     * Checks for edge cases to decide if the next move is valid.
     *
     * @param destination Destination coordinate.
     * @return True if the next move is valid.
     */
    protected abstract boolean isIllegalMove(final int destination);

    public boolean isWhite() {
        return alliance == Alliance.WHITE;
    }

    public boolean isBlack() {
        return !isWhite();
    }

    public boolean isEnemy(Piece other) {
        if (other != null) {
            return alliance != other.alliance;
        }
        return false;
    }

    @Override
    public String toString() {
        return pieceType.pieceName;
    }

    public boolean isKing() {
        return pieceType == PieceType.KING;
    }

    @AllArgsConstructor
    @Getter
    public enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;
    }
}

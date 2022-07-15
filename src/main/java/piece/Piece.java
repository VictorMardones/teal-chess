package piece;

import board.Board;
import board.Move;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import player.Alliance;

import java.util.Collection;

/**
 * A chess piece.
 * TODO: Split into sliding and non sliding pieces
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Piece {

    protected final int position;
    @Getter
    protected final Alliance alliance;

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
}

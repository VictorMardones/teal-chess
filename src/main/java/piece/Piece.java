package piece;

import board.Board;
import board.Move;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import player.Alliance;

import java.util.List;

/**
 * A chess piece.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Piece {

    private final int position;
    private final Alliance alliance;

    /**
     * Calculates all the moves that a piece can do.
     * @param board Current state of the game board.
     * @return List of possible moves.
     */
    public abstract List<Move> calculateLegalMoves(final Board board);
}

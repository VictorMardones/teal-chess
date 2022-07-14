package piece;

import board.Board;
import board.Move;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import player.Alliance;

import java.util.List;

/**
 * A chess piece.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Piece {

    protected final int position;
    @Getter
    protected final Alliance alliance;

    /**
     * Calculates all the moves that a piece can do.
     * @param board Current state of the game board.
     * @return List of possible moves.
     */
    public abstract List<Move> calculateLegalMoves(final Board board);
}

package player;

import board.Board;
import board.Move;
import lombok.AllArgsConstructor;

/**
 * The transition from one board to another, triggered when a move is performed.
 */
@AllArgsConstructor
public class MoveTransition {

    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;

}

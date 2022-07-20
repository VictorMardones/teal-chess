package move;

import board.Board;
import move.Move;
import lombok.AllArgsConstructor;
import lombok.Getter;
import move.MoveStatus;

/**
 * The transition from one board to another, triggered when a move is performed or checked.
 */
@AllArgsConstructor
public class MoveTransition {

    private final Board board;
    private final Move move;
    @Getter
    private final MoveStatus moveStatus;
}

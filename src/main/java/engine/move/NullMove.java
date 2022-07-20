package engine.move;

import engine.board.Board;

public class NullMove extends Move {
    public NullMove() {
        super(null, null, -1);
    }

    @Override
    public Board execute() {
        throw new IllegalStateException("Cannot execute an impossible move!");
    }
}

package player;

/**
 * Tells whether the move is possible or not, including the reason.
 */
public enum MoveStatus {
    DONE;

    public boolean isDone() {
        return this == DONE;
    }
}

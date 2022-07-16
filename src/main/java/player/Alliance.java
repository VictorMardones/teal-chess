package player;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the chess piece's color, which can be white or black.
 */
@AllArgsConstructor
@Getter
public enum Alliance {
    /**
     * The white side, on the bottom of the board.
     */
    WHITE(-1),
    /**
     * The black side, on the top of the board.
     */
    BLACK(1);

    private final int direction;

    public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
        return switch (this) {
            case WHITE -> whitePlayer;
            case BLACK -> blackPlayer;
        };
    }
}

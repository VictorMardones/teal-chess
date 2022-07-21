package engine.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

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

    /**
     * Chooses the first player of this alliance.
     * @param players Players to choose from
     * @return The chosen player
     */
    public Player choosePlayer(final Collection<Player> players) {
        return players.stream()
                .filter(player -> player.getAlliance() == this)
                .findFirst()
                .orElse(null);
    };
}

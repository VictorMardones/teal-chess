package board;

import player.Alliance;

public class BoardUtils {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 63;
    private static final int ROW_LENGTH = 8;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate >= MIN_TILES && coordinate <= MAX_TILES;
    }

    public static Alliance getTileColor(final int coordinate) {
        if ((coordinate + Math.floor(coordinate / ROW_LENGTH) % 2) % 2 == 0) {
            return Alliance.WHITE;
        }
        return Alliance.BLACK;
    }

    public static boolean sameColor(final int origin, final int destination) {
        return getTileColor(origin) == getTileColor(destination);
    }
}

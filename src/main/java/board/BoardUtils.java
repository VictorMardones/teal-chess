package board;

import player.Alliance;

public class BoardUtils {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 64;
    private static final int NUMBER_OF_ROWS = 8;

    private BoardUtils() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    public static boolean isInsideBoard(final int coordinate) {
        return coordinate >= MIN_TILES && coordinate < MAX_TILES;
    }

    public static int getRow(final int coordinate) {
        return coordinate / NUMBER_OF_ROWS;
    }

    public static int getColumn(final int coordinate) {
        return coordinate % NUMBER_OF_ROWS;
    }

    public static boolean sameRow(final int origin, final int destination) {
        return getRow(origin) == getRow(destination);
    }

    public static boolean sameColumn(final int origin, final int destination) {
        return getColumn(origin) == getColumn(destination);
    }

    public static Alliance getTileColor(final int coordinate) {
        if ((coordinate + (float) coordinate / NUMBER_OF_ROWS % 2) % 2 == 0) {
            return Alliance.WHITE;
        }
        return Alliance.BLACK;
    }

    public static boolean sameColor(final int origin, final int destination) {
        return getTileColor(origin) == getTileColor(destination);
    }

}

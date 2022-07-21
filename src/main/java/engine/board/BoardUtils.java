package engine.board;

import engine.player.Alliance;

public final class BoardUtils {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 64;
    public static final int NUMBER_OF_RANKS = 8;
    private static final char[] COLUMN_NAMES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    private BoardUtils() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    public static boolean isInsideBoard(final int coordinate) {
        return coordinate >= MIN_TILES && coordinate < MAX_TILES;
    }

    public static int getRank(final int coordinate) {
        return NUMBER_OF_RANKS - coordinate / NUMBER_OF_RANKS;
    }

    public static int getColumn(final int coordinate) {
        return coordinate % NUMBER_OF_RANKS;
    }

    public static int getColumnName(final int coordinate) {
        return COLUMN_NAMES[getColumn(coordinate)];
    }

    public static boolean sameRank(final int source, final int destination) {
        return getRank(source) == getRank(destination);
    }

    public static boolean sameColumn(final int source, final int destination) {
        return getColumn(source) == getColumn(destination);
    }

    public static Alliance getTileColor(final int coordinate) {
        if ((coordinate + coordinate / NUMBER_OF_RANKS) % 2 == 0) {
            return Alliance.WHITE;
        }
        return Alliance.BLACK;
    }

    public static boolean sameColor(final int source, final int destination) {
        return getTileColor(source) == getTileColor(destination);
    }
}

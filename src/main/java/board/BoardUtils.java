package board;

public class BoardUtils {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 63;
    private static final int COLUMN_LENGTH = 8;

    public static final boolean[] FIRST_COLUMN = initColumn(0);

    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);

    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    private static boolean[] initColumn(int columnNumber) {
        final var column = new boolean[MAX_TILES + 1];

        do {
            column[columnNumber] = true;
            columnNumber += COLUMN_LENGTH;
        } while (columnNumber <= MAX_TILES);

        return column;
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate >= MIN_TILES && coordinate <= MAX_TILES;
    }
}

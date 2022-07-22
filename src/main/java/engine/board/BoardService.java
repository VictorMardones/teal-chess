package engine.board;

import engine.player.Alliance;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardService {

    public static final int MIN_TILES = 0;
    public static final int MAX_TILES = 64;
    public static final int NUMBER_OF_RANKS = 8;
    private static final char[] COLUMN_NAMES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public boolean isInside(final int coordinate) {
        return coordinate >= MIN_TILES && coordinate < MAX_TILES;
    }

    public int getRank(final int coordinate) {
        return NUMBER_OF_RANKS - coordinate / NUMBER_OF_RANKS;
    }

    public int getColumn(final int coordinate) {
        return coordinate % NUMBER_OF_RANKS;
    }

    public int getColumnName(final int coordinate) {
        return COLUMN_NAMES[getColumn(coordinate)];
    }

    public boolean sameRank(final int source, final int destination) {
        return getRank(source) == getRank(destination);
    }

    public boolean sameColumn(final int source, final int destination) {
        return getColumn(source) == getColumn(destination);
    }

    public Alliance getTileColor(final int coordinate) {
        if ((coordinate + coordinate / BoardService.NUMBER_OF_RANKS) % 2 == 0) {
            return Alliance.WHITE;
        }
        return Alliance.BLACK;
    }

    public boolean sameColor(final int source, final int destination) {
        return getTileColor(source) == getTileColor(destination);
    }
}

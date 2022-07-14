package board;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import piece.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * A single chess tile, which may or may not contain a piece.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Tile {

    private final int coordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTiles = new HashMap<>();

        for (var i = 0; i <= BoardUtils.MAX_TILES; i++) {
            emptyTiles.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTiles);
    }

    /**
     * Factory method for creating a new tile.
     *
     * @param coordinate The tile's coordinate.
     * @param piece      The piece on the tile.
     * @return A new tile.
     */
    public Tile create(final int coordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(coordinate, piece) : EMPTY_TILES_CACHE.get(coordinate);
    }

    /**
     * Checks whether the tile contains a piece or not.
     *
     * @return True if the piece contains a piece.
     */
    public boolean isOccupied() {
        return getPiece() != null;
    }

    /**
     * Obtains the piece contained by the tile.
     *
     * @return Piece on the tile.
     */
    public abstract Piece getPiece();

    static final class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    static final class OccupiedTile extends Tile {

        private final Piece piece;

        private OccupiedTile(final int coordinate, final Piece piece) {
            super(coordinate);
            this.piece = piece;
        }

        @Override
        public Piece getPiece() {
            return piece;
        }
    }
}

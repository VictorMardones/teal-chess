package tile;

import lombok.AllArgsConstructor;
import piece.Piece;

/**
 * A single chess tile.
 */
@AllArgsConstructor
public abstract class Tile {

    protected int coordinate;

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

}

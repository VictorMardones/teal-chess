package tile;

import piece.Piece;

/**
 * A tile that doesn't contain a piece.
 */
public final class EmptyTile extends Tile {

    public EmptyTile(int tileCoordinate) {
        super(tileCoordinate);
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}

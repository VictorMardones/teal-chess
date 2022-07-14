package tile;

import piece.Piece;

/**
 * A tile that contains a piece.
 */
public final class OccupiedTile extends Tile {

    private Piece piece;

    public OccupiedTile(int coordinate, Piece piece) {
        super(coordinate);
        this.piece = piece;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}

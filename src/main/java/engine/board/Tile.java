package engine.board;

import com.google.common.collect.ImmutableList;
import engine.piece.Piece;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** A single chess tile, which may or may not contain a piece. */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public abstract class Tile {

  private final int coordinate;

  private static final List<EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

  private static List<EmptyTile> createAllPossibleEmptyTiles() {
    return IntStream.range(BoardService.MIN_TILES, BoardService.MAX_TILES)
        .mapToObj(EmptyTile::new)
        .collect(ImmutableList.toImmutableList());
  }

  /**
   * Factory method for creating a new tile.
   *
   * @param coordinate The tile's coordinate.
   * @param piece The piece on the tile.
   * @return A new tile.
   */
  public static Tile create(final int coordinate, final Piece piece) {
    return piece != null ? new OccupiedTile(coordinate, piece) : EMPTY_TILES_CACHE.get(coordinate);
  }

  /**
   * Obtains the piece contained by the tile.
   *
   * @return Piece on the tile.
   */
  public abstract Optional<Piece> getPiece();

  static final class EmptyTile extends Tile {

    private EmptyTile(final int coordinate) {
      super(coordinate);
    }

    @Override
    public Optional<Piece> getPiece() {
      return Optional.empty();
    }

    @Override
    public String toString() {
      return "-";
    }
  }

  static final class OccupiedTile extends Tile {

    private final Piece piece;

    private OccupiedTile(final int coordinate, final Piece piece) {
      super(coordinate);
      this.piece = piece;
    }

    @Override
    public Optional<Piece> getPiece() {
      return Optional.ofNullable(piece);
    }

    @Override
    public String toString() {
      return piece.isBlack() ? piece.toChar().toLowerCase() : piece.toChar();
    }
  }
}

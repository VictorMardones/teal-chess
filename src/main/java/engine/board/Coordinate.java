package engine.board;

import com.google.common.collect.ImmutableList;
import engine.player.Alliance;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Coordinate {

  private static final String COLUMN_NAMES = "abcdefgh";
  private static final Pattern ALGEBRAIC_PATTERN = Pattern.compile("^[a-h][1-8]$");

  private static final List<Coordinate> COORDINATES_CACHE = createAllPossibleCoordinates();

  private static List<Coordinate> createAllPossibleCoordinates() {
    return IntStream.range(Board.MIN_TILES, Board.MAX_TILES)
        .mapToObj(Coordinate::new)
        .collect(ImmutableList.toImmutableList());
  }

  private final int index;

  private Coordinate(int index) {
    if (isOutsideBoard(index)) {
      throw new InvalidCoordinateException("Index is outside chessboard: " + index);
    }

    this.index = index;
  }

  private static boolean isOutsideBoard(final int index) {
    return index < Board.MIN_TILES || index >= Board.MAX_TILES;
  }

  public static Coordinate of(final String algebraic) {
    if (!ALGEBRAIC_PATTERN.matcher(algebraic).matches()) {
      throw new InvalidCoordinateException("Invalid algebraic notation: " + algebraic);
    }

    return COORDINATES_CACHE.get(calculateIndex(algebraic));
  }

  public static Coordinate of(final int index) {
    if (isOutsideBoard(index)) {
      throw new InvalidCoordinateException("Index is outside chessboard: " + index);
    }

    return COORDINATES_CACHE.get(index);
  }

  public int index() {
    return index;
  }

  private static int calculateIndex(final String algebraicCoordinate) {
    final var column = COLUMN_NAMES.indexOf(algebraicCoordinate.charAt(0));
    final var rank =
        Board.NUMBER_OF_RANKS
            * (Board.NUMBER_OF_RANKS - Integer.parseInt("" + algebraicCoordinate.charAt(1)));

    return column + rank;
  }

  public String asAlgebraic() {
    return "" + getColumn() + getRank();
  }

  public int getRank() {
    return Board.NUMBER_OF_RANKS - index / Board.NUMBER_OF_RANKS;
  }

  public boolean sameRankAs(Coordinate other) {
    return getRank() == other.getRank();
  }

  public char getColumn() {
    return COLUMN_NAMES.charAt(getColumnIndex());
  }

  public boolean sameColumnAs(Coordinate other) {
    return getColumn() == other.getColumn();
  }

  public int getColumnIndex() {
    return index % Board.NUMBER_OF_RANKS;
  }

  public Alliance getColor() {
    if ((index + index / Board.NUMBER_OF_RANKS) % 2 == 0) {
      return Alliance.WHITE;
    }

    return Alliance.BLACK;
  }

  public boolean sameColorAs(Coordinate other) {
    return getColor() == other.getColor();
  }

  /**
   * Gets the coordinate at a relative position.
   *
   * @param x X movement, positive goes down
   * @param y Y movement, positive goes right
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> to(final int x, final int y) {
    try {
      final var destination = COORDINATES_CACHE.get(index + x + Board.NUMBER_OF_RANKS * y);

      if (y == 0 && !sameRankAs(destination)) {
        return Optional.empty();
      }

      return Optional.of(destination);
    } catch (IndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  public Optional<Coordinate> up(final int spaces) {
    return down(-spaces);
  }

  public Optional<Coordinate> down(final int spaces) {
    return to(0, spaces);
  }

  public Optional<Coordinate> left(final int spaces) {
    return right(-spaces);
  }

  public Optional<Coordinate> right(final int spaces) {
    return to(spaces, 0);
  }
}

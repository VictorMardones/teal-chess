/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.board;

import cl.vmardones.chess.engine.player.Alliance;
import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.annotation.MatchesPattern;

/**
 * A coordinate is one of the 64 positions where a chess piece can be. It's usually identified by
 * the chess algebraic notation, which consists of the coordinate's rank (a-h) folowed by its column
 * (1-8).
 */
public final class Coordinate {

  private static final String ALGEBRAIC_PATTERN = "^[a-h][1-8]$";

  private static final List<Coordinate> COORDINATES_CACHE = createAllPossibleCoordinates();

  private static List<Coordinate> createAllPossibleCoordinates() {
    return IntStream.range(Board.MIN_TILES, Board.MAX_TILES)
        .mapToObj(Coordinate::new)
        .collect(ImmutableList.toImmutableList());
  }

  @Min(Board.MIN_TILES)
  @Max(Board.MAX_TILES - 1)
  private final int index;

  /* Index notation initialization */

  private Coordinate(final int index) {
    this.index = index;
  }

  /**
   * Create a coordinate, using an array index. Generally used when creating every coordinate, one
   * by one.
   *
   * @param index The array index of the coordinate
   * @return The created coordinate
   */
  public static Coordinate of(final int index) {
    return COORDINATES_CACHE.get(index);
  }

  /* Algebraic notation initialization */

  /**
   * Create a coordinate, indicating its position with chess algebraic notation.
   *
   * @param algebraic The algebraic notation of the coordinate
   * @return The created coordinate
   * @throws InvalidCoordinateException If the coordinate isn't inside the chessboard
   */
  public static Coordinate of(@NotNull @MatchesPattern(ALGEBRAIC_PATTERN) final String algebraic) {
    return COORDINATES_CACHE.get(calculateIndex(algebraic));
  }

  private static int calculateIndex(final String algebraicCoordinate) {
    final var column = Column.indexOf(algebraicCoordinate.charAt(0));
    final var rank =
        Board.SIDE_LENGTH
            * (Board.SIDE_LENGTH - Integer.parseInt("" + algebraicCoordinate.charAt(1)));

    return column + rank;
  }

  /* Getters and comparations */

  /**
   * Get the array index of this coordinate. Indexes start at 0 in the a8 coordinate, and end at 63
   * in the h1 coordinate.
   *
   * @return This coordinate's index
   */
  public int index() {
    return index;
  }

  /**
   * Obtains the column of this coordinate. The column is a letter between lowercase a (left side or
   * queen's side) and lowercase h (right side or king's side).
   *
   * @return The coordinate's column
   */
  public char getColumn() {
    return Column.getByIndex(getColumnIndex());
  }

  /**
   * Compares this coordinate with another, to see if they are on the same column.
   *
   * @param other The other coordinate
   * @return True if both are on the same column
   */
  public boolean sameColumnAs(@NotNull final Coordinate other) {
    return getColumn() == other.getColumn();
  }

  /**
   * Obtains the column of this coordinate as a number. The a column's index is 0 and the h column's
   * index is 8.
   *
   * @return The coordinate's column, as an index
   */
  public int getColumnIndex() {
    return index % Board.SIDE_LENGTH;
  }

  /**
   * Obtains the rank (row in chess terminology) of this coordinate. The rank is a number between 1
   * (bottom of the board, white side) and 8 (top of the board, black side).
   *
   * @return The coordinate's rank
   */
  public int getRank() {
    return Board.SIDE_LENGTH - index / Board.SIDE_LENGTH;
  }

  /**
   * Compares this coordinate with another, to see if they are on the same rank.
   *
   * @param other The other coordinate
   * @return True if both are on the same rank
   */
  public boolean sameRankAs(@NotNull final Coordinate other) {
    return getRank() == other.getRank();
  }

  /**
   * Convert the coordinate to algebraic notation.
   *
   * @return The coordinate's name
   */
  @Override
  public String toString() {
    return "" + getColumn() + getRank();
  }

  /**
   * Obtains the color of this coordinate.
   *
   * @return The color, which is either black or white
   */
  public Alliance getColor() {
    if ((index + index / Board.SIDE_LENGTH) % 2 == 0) {
      return Alliance.WHITE;
    }

    return Alliance.BLACK;
  }

  /**
   * Compares this coordinate with another, to see if they have the same color.
   *
   * @param other The other coordinate
   * @return True if both are the same color
   */
  public boolean sameColorAs(@NotNull final Coordinate other) {
    return getColor() == other.getColor();
  }

  /* Traslation operations */

  /**
   * Gets the coordinate at a relative position.
   *
   * @param x X axis movement, positive goes right
   * @param y Y axis movement, positive goes up
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> to(final int x, final int y) {
    try {
      final var destination =
          COORDINATES_CACHE.get(index + horizontalClamp(x) - y * Board.SIDE_LENGTH);

      if (illegalJump(x, destination)) {
        return Optional.empty();
      }

      return Optional.of(destination);
    } catch (final IndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  private boolean illegalJump(final int x, final Coordinate destination) {
    return x < 0 && destination.getColumnIndex() > getColumnIndex()
        || x > 0 && destination.getColumnIndex() < getColumnIndex();
  }

  private int horizontalClamp(final int x) {
    return x % Board.SIDE_LENGTH;
  }

  /**
   * Get the coordinate X spaces upwards from this coordinate.
   *
   * @param spaces Number of spaces to jump, it can also be negative to move backwards
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> up(final int spaces) {
    return to(0, spaces);
  }

  /**
   * Get the coordinate X spaces downwards from this coordinate.
   *
   * @param spaces Number of spaces to jump, it can also be negative to move backwards
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> down(final int spaces) {
    return up(-spaces);
  }

  /**
   * Get the coordinate X spaces to the left of this coordinate.
   *
   * @param spaces Number of spaces to jump, it can also be negative to move backwards
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> left(final int spaces) {
    return right(-spaces);
  }

  /**
   * Get the coordinate X spaces to the right of this coordinate.
   *
   * @param spaces Number of spaces to jump, it can also be negative to move backwards
   * @return Coordinate at the relative position, if it is inside the board
   */
  public Optional<Coordinate> right(final int spaces) {
    return to(spaces, 0);
  }

  private enum Column {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H;

    private static final List<Character> names =
        Arrays.stream(values()).map(Column::getName).toList();

    private static char getByIndex(final int index) {
      return values()[index].getName();
    }

    private static int indexOf(final Character name) {
      return names.indexOf(name);
    }

    private char getName() {
      return name().toLowerCase().charAt(0);
    }
  }
}

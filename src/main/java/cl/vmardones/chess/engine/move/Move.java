/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.move;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.piece.Piece;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/** The action of moving a piece. */
@Slf4j
@EqualsAndHashCode
public abstract class Move {

  protected final Board board;

  @Getter protected final Piece piece;

  @Getter protected final Coordinate destination;

  protected final boolean firstMove;

  @Getter protected boolean castling = false;

  @Getter protected Piece capturedPiece;

  protected Move(final Board board, final Piece piece, final Coordinate destination) {
    this.board = board;
    this.piece = piece;
    this.destination = destination;
    firstMove = piece.isFirstMove();
  }

  public boolean isFirstMove() {
    return firstMove;
  }

  public boolean isCapturing() {
    return capturedPiece != null;
  }

  /**
   * When a move is performed, a new board is created, because the board class is immutable.
   *
   * @return The new board, after the move was performed
   */
  public Board execute() {
    return board.nextTurnBuilder().withoutPiece(piece).piece(piece.move(this)).build();
  }

  public Coordinate getSource() {
    return piece.getPosition();
  }

  @Override
  public String toString() {
    return getDestination().toString();
  }

  public boolean isNull() {
    return piece.getPosition() == destination;
  }

  public static final class MoveFactory {

    private MoveFactory() {
      throw new UnsupportedOperationException("You cannot instantiate me!");
    }

    /**
     * Creates a move in the specified direction.
     *
     * @param source Source coordinate
     * @param destination Destination coordinate
     * @return Move that goes from the source to the destination, if possible.
     */
    public static Optional<Move> create(
        @NonNull final Collection<Move> currentPlayerLegals,
        @NonNull final Coordinate source,
        @NonNull final Coordinate destination) {
      if (source.equals(destination)) {
        return Optional.empty();
      }

      log.debug(
          "Current piece legals: {}",
          currentPlayerLegals.stream().filter(move -> move.getSource().equals(source)).toList());

      return currentPlayerLegals.stream().filter(isMovePossible(source, destination)).findFirst();
    }

    private static Predicate<Move> isMovePossible(
        final Coordinate source, final Coordinate destination) {
      return move -> move.getSource().equals(source) && move.getDestination() == destination;
    }
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.player;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.move.*;
import cl.vmardones.chess.engine.piece.King;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.piece.Rook;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

/**
 * The entity that controls the pieces in one side of the board. It can be controlled either by a
 * human or an AI.
 */
@ToString
public abstract class Player {

  @ToString.Exclude protected final Board board;

  @Getter protected final King king;

  @Getter @ToString.Exclude protected final Collection<Move> legals;
  @ToString.Exclude protected final Collection<Move> opponentLegals;

  private final boolean inCheck;
  private Boolean noEscapeMoves;

  protected Player(
      final Board board,
      final King king,
      final Collection<Move> legals,
      final Collection<Move> opponentLegals) {
    this.board = board;
    this.king = king;
    this.opponentLegals = opponentLegals;

    this.legals = ImmutableList.copyOf(Iterables.concat(legals, calculateCastles(opponentLegals)));
    inCheck = !Player.calculateAttacksOnTile(king.getPosition(), opponentLegals).isEmpty();
  }

  protected static Collection<Move> calculateAttacksOnTile(
      @NotNull final Coordinate kingPosition, @NotNull final Collection<Move> moves) {
    return moves.stream()
        .filter(move -> kingPosition == move.getDestination())
        .collect(ImmutableList.toImmutableList());
  }

  /**
   * Used to check if a specific move can be performed.
   *
   * @param move The move to check
   * @return True if the move is legal
   */
  public boolean isLegal(final Move move) {
    return legals.contains(move);
  }

  /**
   * Checks if the player is in check, which means that the king must be protected.
   *
   * @return True if the player is in check
   */
  public boolean isInCheck() {
    return inCheck;
  }

  /**
   * Checks if the player is in checkmate, which means the game is lost. This happens when the king
   * is in check and there are no escape moves.
   *
   * @return True if the player is in checkmate
   */
  public boolean isInCheckmate() {
    return isInCheck() && hasNoEscapeMoves();
  }

  private boolean hasNoEscapeMoves() {
    if (noEscapeMoves == null) {
      noEscapeMoves =
          legals.stream()
              .map(move -> makeMove(this, move))
              .noneMatch(transition -> transition.getMoveStatus().isDone());
    }

    return noEscapeMoves;
  }

  /**
   * Checks if the player is in stalemate, which means that game ends in a tie. This happens when
   * the king isn't in check and there are no escape moves.
   *
   * @return True if the player is in stalemate
   */
  public boolean inInStalemate() {
    return !isInCheck() && hasNoEscapeMoves();
  }

  public boolean isCastled() {
    return false;
  }

  public MoveTransition makeMove(@NotNull final Player currentPlayer, @NotNull final Move move) {
    if (move.isNull()) {
      return new MoveTransition(board, move, MoveStatus.NULL);
    }

    if (!isLegal(move)) {
      return new MoveTransition(board, move, MoveStatus.ILLEGAL);
    }

    final Collection<Move> kingAttacks =
        Player.calculateAttacksOnTile(currentPlayer.getKing().getPosition(), opponentLegals);

    if (!kingAttacks.isEmpty()) {
      return new MoveTransition(board, move, MoveStatus.LEAVES_OPPONENT_IN_CHECK);
    }

    return new MoveTransition(move.execute(), move, MoveStatus.DONE);
  }

  /**
   * Obtains the player's current pieces on the board.
   *
   * @return The player's active pieces
   */
  public abstract Collection<Piece> getActivePieces();

  /**
   * Obtains the player's side.
   *
   * @return The player's alliance
   */
  public abstract Alliance getAlliance();

  // TODO: Refactor this method, maybe use combinator pattern
  protected Collection<Move> calculateCastles(@NotNull final Collection<Move> opponentLegals) {

    final List<Move> castles = new ArrayList<>();

    if (!king.isFirstMove() || isInCheck() || king.getPosition().getColumn() != 'e') {
      return ImmutableList.copyOf(castles);
    }

    final var kingPosition = king.getPosition();

    if (isKingSideCastlePossible(kingPosition, opponentLegals)) {
      final var rook = (Rook) board.getTile(kingPosition.right(3).get()).getPiece().get();
      final var kingDestination = kingPosition.right(2).get();
      final var rookDestination = kingPosition.right(1).get();

      if (rook.isFirstMove()) {
        castles.add(new KingSideCastleMove(board, king, kingDestination, rook, rookDestination));
      }
    }

    if (isQueenSideCastlePossible(kingPosition, opponentLegals)) {
      final var rook = (Rook) board.getTile(kingPosition.right(3).get()).getPiece().get();
      final var kingDestination = kingPosition.left(2).get();
      final var rookDestination = kingPosition.left(1).get();

      if (rook.isFirstMove()) {
        castles.add(new QueenSideCastleMove(board, king, kingDestination, rook, rookDestination));
      }
    }

    return ImmutableList.copyOf(castles);
  }

  private boolean isKingSideCastlePossible(
      final Coordinate kingPosition, final Collection<Move> opponentLegals) {
    return isTileFree(kingPosition, 1)
        && isTileFree(kingPosition, 2)
        && isTileRook(kingPosition, 3)
        && isUnreachableByEnemy(kingPosition, 1, opponentLegals)
        && isUnreachableByEnemy(kingPosition, 2, opponentLegals);
  }

  private boolean isQueenSideCastlePossible(
      final Coordinate kingPosition, final Collection<Move> opponentLegals) {
    return isTileFree(kingPosition, -1)
        && isTileFree(kingPosition, -2)
        && isTileFree(kingPosition, -3)
        && isTileRook(kingPosition, -4)
        && isUnreachableByEnemy(kingPosition, -1, opponentLegals)
        && isUnreachableByEnemy(kingPosition, -2, opponentLegals)
        && isUnreachableByEnemy(kingPosition, -3, opponentLegals);
  }

  private boolean isTileFree(final Coordinate kingPosition, final int offset) {
    final var destination = kingPosition.right(offset);

    return destination.isPresent() && board.containsNothing(destination.get());
  }

  private boolean isUnreachableByEnemy(
      final Coordinate kingPosition, final int offset, final Collection<Move> opponentLegals) {
    final var destination = kingPosition.right(offset);

    return destination.isPresent()
        && Player.calculateAttacksOnTile(destination.get(), opponentLegals).isEmpty();
  }

  private boolean isTileRook(final Coordinate kingPosition, final int offset) {
    final var destination = kingPosition.right(offset);

    return destination.isPresent() && board.contains(destination.get(), Piece.PieceType.ROOK);
  }
}

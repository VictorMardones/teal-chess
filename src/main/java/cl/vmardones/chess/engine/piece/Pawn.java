/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.engine.piece;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.board.Tile;
import cl.vmardones.chess.engine.move.*;
import cl.vmardones.chess.engine.piece.vector.Diagonal;
import cl.vmardones.chess.engine.piece.vector.Jump;
import cl.vmardones.chess.engine.piece.vector.Vector;
import cl.vmardones.chess.engine.piece.vector.Vertical;
import cl.vmardones.chess.engine.player.Alliance;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The pawn piece. It only moves forward (depending on the side) and can eat other pieces
 * diagonally. A very weak piece, but it can be promoted when getting to the last rank at the
 * opposite side.
 */
@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
@Slf4j
public class Pawn implements JumpingPiece {

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Pawn(final Coordinate position, final Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.PAWN;
  }

  @Override
  public Optional<Move> createMove(final Tile destination, final Board board) {

    if (isCaptureMove(destination)) {
      if (!isEnPassantPossible(board, destination)) {
        return createCaptureMove(board, destination);
      }

      log.debug("En passant is possible!");
      return createEnPassantMove(board, destination);
    }

    if (isJumpPossible(board, destination)) {
      return createJumpMove(board, destination);
    }

    return createForwardMove(board, destination);
  }

  private Optional<Move> createEnPassantMove(final Board board, final Tile destination) {
    final var enPassantMove =
        new EnPassantMove(board, this, destination.getCoordinate(), board.getEnPassantPawn());

    log.debug("Created en passant move: {}", enPassantMove);
    return Optional.of(enPassantMove);
  }

  private boolean isEnPassantPossible(final Board board, final Tile destination) {

    if (board.getEnPassantPawn() == null) {
      return false;
    }

    final var side = destination.getCoordinate().to(0, alliance.getOppositeDirection());

    if (side.isEmpty()) {
      return false;
    }

    final var pieceAtSide = board.getTile(side.get()).getPiece();

    return pieceAtSide.isPresent()
        && pieceAtSide.get().equals(board.getEnPassantPawn())
        && destination.getPiece().isEmpty();
  }

  private boolean isCaptureMove(final Tile destination) {
    return !getPosition().sameColumnAs(destination.getCoordinate());
  }

  private Optional<Move> createCaptureMove(final Board board, final Tile destination) {
    final var capturablePiece = destination.getPiece();

    if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
      return Optional.of(
          new PawnCaptureMove(board, this, destination.getCoordinate(), capturablePiece.get()));
    }

    return Optional.empty();
  }

  private Optional<Move> createJumpMove(final Board board, final Tile destination) {
    return Optional.of(new PawnJump(board, this, destination.getCoordinate()));
  }

  private boolean isJumpPossible(final Board board, final Tile destination) {

    final var forward = position.up(alliance.getDirection());

    if (forward.isEmpty()) {
      return false;
    }

    return isFirstMove() && isAccessible(board.getTile(forward.get())) && isAccessible(destination);
  }

  private Optional<Move> createForwardMove(final Board board, final Tile destination) {
    if (destination.getPiece().isPresent()) {
      return Optional.empty();
    }

    // TODO: Deal with promotions
    return Optional.of(new PawnMove(board, this, destination.getCoordinate()));
  }

  @Override
  public Pawn move(final Move move) {
    return new Pawn(move.getDestination(), alliance, false);
  }

  @Override
  public Collection<int[]> getMoveOffsets() {
    return switch (getAlliance()) {
      case BLACK -> calculateBlackOffsets();
      case WHITE -> calculateWhiteOffsets();
    };
  }

  private Collection<int[]> calculateWhiteOffsets() {
    final List<Vector> moves =
        new ArrayList<>(List.of(Vertical.UP, Diagonal.UP_LEFT, Diagonal.UP_RIGHT));

    if (isFirstMove()) {
      moves.add(Jump.UP);
    }

    return moves.stream().map(Vector::getVector).collect(ImmutableList.toImmutableList());
  }

  private Collection<int[]> calculateBlackOffsets() {
    final List<Vector> moves =
        new ArrayList<>(List.of(Vertical.DOWN, Diagonal.DOWN_LEFT, Diagonal.DOWN_RIGHT));

    if (isFirstMove()) {
      moves.add(Jump.DOWN);
    }

    return moves.stream().map(Vector::getVector).collect(ImmutableList.toImmutableList());
  }
}

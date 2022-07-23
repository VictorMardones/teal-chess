package engine.piece;

import com.google.common.collect.ImmutableList;
import engine.board.Board;
import engine.board.Coordinate;
import engine.board.Tile;
import engine.move.*;
import engine.player.Alliance;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The pawn piece. It only moves forward (depending on the side) and can eat other pieces
 * diagonally. A very weak piece, but it can be promoted when getting to the last rank at the
 * opposite side.
 */
@Getter
@AllArgsConstructor
public final class Pawn implements Piece {

  private Coordinate position;
  private Alliance alliance;
  private boolean firstMove;

  public Pawn(Coordinate position, Alliance alliance) {
    this(position, alliance, true);
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.PAWN;
  }

  // TODO: Refactor this code when the pawn is implemented completely
  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {
/*
    return Arrays.stream(PawnOffset.values())
            -        .filter(pawnOffset -> boardService.isInside(getDestination(pawnOffset)))
            -        .filter(pawnOffset -> isInMoveRange(getDestination(pawnOffset)))
            .map(pawnOffset -> handleOffset(pawnOffset, board))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ImmutableList.toImmutableList());
    */

    return calculatePossibleDestinations()
            .stream()
        .map(pawnOffset -> handleOffset(pawnOffset, board))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(ImmutableList.toImmutableList());
  }

  @Override
  public Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(PawnOffset.values())
            .map(this::getDestination)
            .map(Coordinate::new)
            .filter(destination ->  Math.abs(position.getColumnIndex() - destination.getColumnIndex()) <= 1)
            .collect(ImmutableList.toImmutableList());
  }

  private int getDestination(PawnOffset pawnOffset) {
    return position.index() + getAlliance().getDirection() * pawnOffset.offset;
  }

  private Optional<Move> handleOffset(PawnOffset pawnOffset, Board board) {
    var destination = getDestination(pawnOffset);

    return switch (pawnOffset) {
      case FIRST_MOVE -> createFirstMove(board, destination);
      case FORWARD_MOVE -> createForwardMove(board, destination);
      case LEFT_CAPTURE, RIGHT_CAPTURE -> createCaptureMove(board, destination);
    };
  }

  @Override
  public Optional<Move> createMove(Tile destination, Board board) {

  }

  private Optional<Move> createFirstMove(Board board, Coordinate destination) {
    if (!isFirstMovePossible(board)) {
      return Optional.empty();
    }

    return Optional.of(new PawnJump(board, this, destination));
  }

  private Optional<Move> createForwardMove(Board board, Coordinate destination) {
    if (board.getTile(destination).getPiece().isPresent()) {
      return Optional.empty();
    }

    // TODO: Deal with promotions
    return Optional.of(new PawnMove(board, this, destination));
  }

  private Optional<Move> createCaptureMove(Board board, Coordinate destination) {
    final var capturablePiece = board.getTile(destination).getPiece();

    if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
      return Optional.of(new PawnCaptureMove(board, this, destination, capturablePiece.get()));
    }

    return Optional.empty();
  }

  private boolean isFirstMovePossible(final Board board) {
    return board
            .getTile(position + PawnOffset.FIRST_MOVE.offset * alliance.getDirection())
            .getPiece()
            .isEmpty()
        && board
            .getTile(position + PawnOffset.FORWARD_MOVE.offset * alliance.getDirection())
            .getPiece()
            .isEmpty();
  }

  @Override
  public Pawn move(final Move move) {
    return new Pawn(move.getDestination(), alliance, false);
  }

  @AllArgsConstructor
  private enum PawnOffset {
    FIRST_MOVE(16),
    FORWARD_MOVE(8),
    LEFT_CAPTURE(7),
    RIGHT_CAPTURE(9);

    private final int offset;
  }
}

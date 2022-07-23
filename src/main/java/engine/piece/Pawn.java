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

  private static final int[] MOVE_OFFSETS = {7, 8, 9, 16};

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

  @Override
  public Collection<Coordinate> calculatePossibleDestinations() {
    return Arrays.stream(MOVE_OFFSETS)
            .mapToObj(this::getDestination)
            .map(Coordinate::new)
            .filter(destination ->  Math.abs(position.getColumnIndex() - destination.getColumnIndex()) <= 1)
            .collect(ImmutableList.toImmutableList());
  }

  private int getDestination(int offset) {
    return position.index() + getAlliance().getDirection() * offset;
  }

  @Override
  public Optional<Move> createMove(Tile destination, Board board) {
    if(isCaptureMove(destination)) {
      return createCaptureMove(board, destination.getCoordinate());
    }

    if(isFirstMovePossible(board)) {
      return createFirstMove(board, destination.getCoordinate());
    }

    return createForwardMove(board, destination.getCoordinate());
  }

  private boolean isCaptureMove(Tile destination) {
    return !getPosition().sameColumnAs(destination.getCoordinate());
  }

  private Optional<Move> createCaptureMove(Board board, Coordinate destination) {
    final var capturablePiece = board.getTile(destination).getPiece();

    if (capturablePiece.isPresent() && isEnemyOf(capturablePiece.get())) {
      return Optional.of(new PawnCaptureMove(board, this, destination, capturablePiece.get()));
    }

    return Optional.empty();
  }

  private Optional<Move> createFirstMove(Board board, Coordinate destination) {
    return Optional.of(new PawnJump(board, this, destination));
  }

  private boolean isFirstMovePossible(final Board board) {

    var forward = position.down(alliance.getDirection());
    var destination = position.down(2 * alliance.getDirection());

    if (forward.isEmpty() || destination.isEmpty()) {
      return false;
    }

    return isFirstMove()
            && board
            .getTile(forward.get())
            .getPiece()
            .isEmpty()
            && board
            .getTile(destination.get())
            .getPiece()
            .isEmpty();
  }

  private Optional<Move> createForwardMove(Board board, Coordinate destination) {
    if (board.getTile(destination).getPiece().isPresent()) {
      return Optional.empty();
    }

    // TODO: Deal with promotions
    return Optional.of(new PawnMove(board, this, destination));
  }

  @Override
  public Pawn move(final Move move) {
    return new Pawn(move.getDestination(), alliance, false);
  }
}

package engine.move;

import engine.board.Board;
import engine.board.Board.Builder;
import engine.board.Coordinate;
import engine.piece.Piece;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/** The action of moving a piece. */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString(includeFieldNames = false)
public abstract class Move {

  @ToString.Exclude protected final Board board;
  @Getter protected final Piece piece;
  @Getter private final Coordinate destination;

  @Getter protected boolean castling = false;
  @Getter protected Piece capturedPiece;

  public boolean isFirstMove() {
    return piece.isFirstMove();
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

    final var builder =
        new Builder(board.getWhitePlayer().getKing(), board.getBlackPlayer().getKing());

    board.getCurrentPlayer().getActivePieces().stream()
        .filter(activePiece -> !piece.equals(activePiece))
        .forEach(builder::withPiece);

    board.getCurrentPlayer().getOpponent().getActivePieces().forEach(builder::withPiece);

    builder.withPiece(piece.move(this));
    builder.withNextTurn(board.getCurrentPlayer().getOpponent().getAlliance());
    return builder.build();
  }

  public Coordinate getSource() {
    return piece.getPosition();
  }

  public static final class Factory {

    private Factory() {
      throw new IllegalStateException("You cannot instantiate me!");
    }

    /**
     * Creates a move in the specified direction.
     *
     * @param board The chessboard.
     * @param source Source coordinate.
     * @param destination Destination coordinate.
     * @return Move that goes from the source to the destination, if possible.
     */
    public static Optional<Move> create(
        final Board board, final Coordinate source, final Coordinate destination) {
      return board.getCurrentPlayerLegalMoves().stream()
          .filter(isMovePossible(source, destination))
          .findFirst();
    }

    private static Predicate<Move> isMovePossible(Coordinate source, Coordinate destination) {
      return move -> move.getSource() == source && move.getDestination() == destination;
    }
  }
}

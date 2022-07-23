package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;
import lombok.Getter;

/**
 * The king piece. The most important piece in the game, must be defended at all costs. It moves
 * like the queen, but only one space at a time. It also cannot move into a position where it could
 * be captured.
 */
@Getter
public final class King extends JumpingPiece {

  public King(int position, Alliance alliance, boolean firstMove, BoardService boardService) {
    super(position, alliance, firstMove, boardService);
  }

  public King(int position, Alliance alliance, BoardService boardService) {
    this(position, alliance, true, boardService);
  }

  @Override
  public int[] getMoveOffsets() {
    return new int[] {-9, -8, -7, -1, 1, 7, 8, 9};
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.KING;
  }

  @Override
  public boolean isInMoveRange(int destination) {
    return Math.abs(boardService.getColumn(position) - boardService.getColumn(destination)) <= 2;
  }

  @Override
  public King move(final Move move) {
    return new King(move.getDestination(), alliance, false, boardService);
  }
}

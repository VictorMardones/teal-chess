package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;

/** The bishop piece. It can move diagonally. */
public final class Bishop extends SlidingPiece {

  private static final int[] MOVE_VECTORS = {-9, -7, 7, 9};

  public Bishop(int position, Alliance alliance, BoardService boardService) {
    super(position, alliance, boardService);
  }

  @Override
  public int[] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.BISHOP;
  }

  @Override
  public boolean isInMoveRange(int destination) {
    return boardService.sameColor(position, destination);
  }

  @Override
  public Bishop move(final Move move) {
    return new Bishop(move.getDestination(), alliance, boardService);
  }
}

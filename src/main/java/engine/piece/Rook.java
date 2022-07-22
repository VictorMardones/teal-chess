package engine.piece;

import engine.board.BoardService;
import engine.move.Move;
import engine.player.Alliance;

/** The rook piece. It can move horizontally and vertically. */
public final class Rook extends SlidingPiece {

  private static final int[] MOVE_VECTORS = {-8, -1, 1, 8};

  public Rook(int position, Alliance alliance, BoardService boardService) {
    super(position, alliance, boardService);
  }

  @Override
  public int[] getMoveVectors() {
    return MOVE_VECTORS;
  }

  @Override
  public PieceType getPieceType() {
    return PieceType.ROOK;
  }

  @Override
  public boolean isInMoveRange(int destination) {
    return getBoardService().sameRank(position, destination)
        || getBoardService().sameColumn(position, destination);
  }

  @Override
  public Rook move(final Move move) {
    return new Rook(move.getDestination(), alliance, boardService);
  }
}

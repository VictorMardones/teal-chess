/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.engine.player;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.King;
import cl.vmardones.chess.engine.piece.Piece;
import java.util.Collection;

/** The player that uses the white pieces. */
public class WhitePlayer extends Player {

  public WhitePlayer(
      final Board board,
      final King king,
      final Collection<Move> legals,
      final Collection<Move> opponentMoves) {
    super(board, king, legals, opponentMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return board.getWhitePieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.WHITE;
  }
}

/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package engine.player;

import engine.board.Board;
import engine.move.Move;
import engine.piece.King;
import engine.piece.Piece;
import java.util.Collection;

/**
 * The player that uses the white pieces.
 */
public class WhitePlayer extends Player {
    public WhitePlayer(Board board, King king, Collection<Move> whiteLegals, Collection<Move> blackLegals) {
        super(board, king, whiteLegals, blackLegals);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return board.getBlackPlayer();
    }
}

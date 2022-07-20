package engine.player;

import engine.board.Board;
import engine.move.Move;
import engine.piece.King;
import engine.piece.Piece;

import java.util.Collection;

/**
 * The player that uses the black pieces.
 */
public class BlackPlayer extends Player {
    public BlackPlayer(Board board, King king, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, king, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return board.getWhitePlayer();
    }
}

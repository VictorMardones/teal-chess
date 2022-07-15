package player;

import board.Board;
import board.Move;
import piece.King;
import piece.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, King king, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, king, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }
}

package player;

import board.Board;
import board.Move;
import piece.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }
}

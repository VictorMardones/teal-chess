package player;

import board.Board;
import board.Move;
import piece.King;
import piece.Piece;

import java.util.Collection;

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, King king, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board, king, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }
}
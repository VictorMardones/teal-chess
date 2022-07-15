package player;

import board.Board;
import board.Move;
import piece.King;
import piece.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;

    public Player(Board board, King king, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.king = king;
    }

    public abstract Collection<Piece> getActivePieces();
}
